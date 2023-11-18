import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import model.LambdaEntryItem;
import model.LambdaResponseBody;
import model.ProgressResponse;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LambdaHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private final String apiKey = "?api_key=3d31acb4-a9ed-470e-8610-5b30a800d764";
    private final String baseUrl = "https://api.globalgiving.org/api/public/projectservice/projects/";
    private final HttpClient client = HttpClient.newHttpClient();
    ArrayList<LambdaEntryItem> lambdaEntries = new ArrayList<>();
    private String countryUrl;
    private String projectId;
    private LambdaResponseBody lambdaResponse;
    private ProgressResponse progressResponse;

    public LambdaHandler() {
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        Map<String, String> queryParams = input.getQueryStringParameters();
        projectId = queryParams.get("projectId");
        if (projectId == null) {
            lambdaEntries.add(new LambdaEntryItem(new ArrayList<>(), "", "", "No parameters supplied."));
            lambdaResponse = new LambdaResponseBody(lambdaEntries);
            String lambdaResponseString = new Gson().toJson(lambdaResponse);
            return generateResponse(TaskStatus.SERVER_ERROR, lambdaResponseString);
        }
        return getProgressReport();
    }

    private APIGatewayProxyResponseEvent getProgressReport() {
        countryUrl = baseUrl + projectId + "/reports" + apiKey;
        HttpRequest progressRequest = HttpRequest.newBuilder().uri(URI.create(countryUrl)).headers("Accept", "application/json").build();
        try {
            HttpResponse<String> response = client.send(progressRequest, HttpResponse.BodyHandlers.ofString());
            if (response.body() != null && !response.body().isEmpty() && !response.body().isBlank()) {
                String responseBody = response.body();
                progressResponse = new Gson().fromJson(responseBody, ProgressResponse.class);
                progressResponse.getEntries().forEach(it -> {
                    lambdaEntries.add(new LambdaEntryItem(it.getAuthors(), it.getId(), it.getPublished(), it.getTitle()));
                });
                lambdaResponse = new LambdaResponseBody(lambdaEntries);
                String lambdaResponseString = new Gson().toJson(lambdaResponse);
                System.out.println(lambdaResponseString);
                return generateResponse(TaskStatus.SUCCESS, lambdaResponseString);
            } else {
                lambdaEntries.add(new LambdaEntryItem(new ArrayList<>(), "", "", "No progress found"));
                lambdaResponse = new LambdaResponseBody(lambdaEntries);
                String lambdaResponseString = new Gson().toJson(lambdaResponse);
                return generateResponse(TaskStatus.NOT_FOUND, lambdaResponseString);
            }
        } catch (Exception e) {
            return generateResponse(TaskStatus.SERVER_ERROR, e.getMessage());
        }
    }

    //Generate API response
    private APIGatewayProxyResponseEvent generateResponse(int taskStatus, String responseBody) {
        return new APIGatewayProxyResponseEvent()
                .withStatusCode(taskStatus)
                .withHeaders(getHeaders())
                .withBody(responseBody);
    }

    //Generate CORS headers
    private Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Access-Control-Allow-Origin", "*");
        headers.put("Access-Control-Allow-Headers", "Origin,X-Requested-With,Content-Type,Accept,Authorization");
        headers.put("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS,HEAD");
        return headers;
    }
}
