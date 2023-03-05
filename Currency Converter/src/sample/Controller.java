package sample;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Controller {
    // currencyOne, currencyTwo - holds the currency tickers (from and to)
    // apiKey - holds the api key to use the api
    private String currencyOne, currencyTwo, apiKey;

    private ArrayList<String> currencyList;

    @FXML
    private ImageView logo;

    @FXML
    private TextField enterAmountField;

    @FXML
    private ComboBox<String> currencyOneBox, currencyTwoBox;

    @FXML
    private Label resultLabel;

    public void initialize(){
        // retrieve api key
        getApiKey();

        // load logo
        loadLogo();

        try{
            // retrieve and store list of currencies
            currencyList = loadCurrencyList();

            // store the list in the combo boxes
            ObservableList<String> options = FXCollections.observableArrayList(currencyList);
            currencyOneBox.setItems(options);
            currencyTwoBox.setItems(options);

        }catch(IOException e){
            System.out.println("Error: Failed to load currency list " + e);
        }

    }

    public void setCurrencyOne(){currencyOne = currencyOneBox.getValue();}
    public void setCurrencyTwo(){currencyTwo = currencyTwoBox.getValue();}

    private void getApiKey(){
        BufferedReader reader = null;
        try{
            String filePath = getClass().getResource("/resources/apikey.txt").getPath()
                    .replaceAll("%20", " ");
            reader = new BufferedReader(new FileReader(filePath));

            // store api key
            apiKey = reader.readLine();

        }catch(IOException e){
            System.out.println("Error: " + e);
        }finally{
            // close the buffered reader object to free up resources
            try{
                if(reader != null) reader.close();
            }catch(IOException e){
                System.out.println("Error: " + e);
            }
        }
    }

    private void loadLogo(){
        String logoPath = getClass().getResource("/resources/logo.png").getPath()
                .replaceAll("%20", " ");
        logo.setImage(new Image(new File(logoPath).getAbsolutePath()));
    }

    private ArrayList<String> loadCurrencyList() throws IOException{
        // retrieve currency list through api call
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        Request request = new Request.Builder()
                .url("https://api.apilayer.com/currency_data/list")
                .addHeader("apikey", apiKey)
                .method("GET", null)
            .build();
        Response response = client.newCall(request).execute();

        Gson gson = new Gson();
        JsonElement jsonElement = gson.fromJson(response.body().charStream(), JsonElement.class);
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        ArrayList<String> currencyList = new ArrayList<>();
        for(String currency : jsonObject.getAsJsonObject("currencies").keySet()){
            currencyList.add(currency);
        }
        return currencyList;
    }

    public void convertCurrency() throws IOException {
        if(enterAmountField.getText().equals("") || enterAmountField.getText() == null) return;
        if(currencyOne == null || currencyTwo == null) return;

        float conversionRate = getConversionRate();

        // calculate conversion
        float conversionResult = Float.parseFloat(enterAmountField.getText()) * conversionRate;

        // display result
        resultLabel.setText(conversionResult + " " + currencyTwo);
    }

    private float getConversionRate() throws IOException{
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        Request request = new Request.Builder()
                .url("https://api.apilayer.com/currency_data/live?source=" + currencyOne + "&currencies=" + currencyTwo)
                .addHeader("apikey", apiKey)
                .method("GET", null)
            .build();
        Response response = client.newCall(request).execute();

        Gson gson = new Gson();
        JsonElement jsonElement = gson.fromJson(response.body().charStream(), JsonElement.class);
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        // return the conversion rate
        String key = currencyOne + currencyTwo;
        return Float.parseFloat(jsonObject.getAsJsonObject("quotes").get(key).getAsString());
    }
}




















