import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class PostgreSQL_Connect {

    private String jdbcUrl = "jdbc:postgresql://localhost:5432/postgres";
    private String username = "postgres";
    private String password = "3TI<l`=~$0";
    private Connection connection;
    private PreparedStatement preparedStatement;

    public PostgreSQL_Connect() {

        try{
            connection = DriverManager.getConnection(jdbcUrl, username, password);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void addCard(Scanner reader) throws SQLException {

        String insertQuery = "INSERT INTO cardinfo (card_name, set, num, bought_price) VALUES (?,?,?,?);";
        preparedStatement = connection.prepareStatement(insertQuery);

        System.out.print("Name of card: ");
        preparedStatement.setString(1, reader.nextLine());

        System.out.print("Set of card: ");
        preparedStatement.setString(2, reader.nextLine());

        System.out.print("Num of card: ");
        preparedStatement.setInt(3, Integer.parseInt(reader.nextLine()));

        System.out.print("Price bought at: ");
        preparedStatement.setDouble(4, Double.parseDouble(reader.nextLine()));
        preparedStatement.executeUpdate();

        preparedStatement.close();
    }

    public void updateCurrentPrice(double price, String name) throws SQLException {
        String updateQuery = "UPDATE cardinfo SET price = ? WHERE card_name = ?";
        preparedStatement = connection.prepareStatement(updateQuery);
        preparedStatement.setDouble(1, price);
        preparedStatement.setString(2, name);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void showCards() throws SQLException {
        String displayQuery = "SELECT * FROM cardinfo;";
        preparedStatement = connection.prepareStatement(displayQuery);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            System.out.println("Name: " + resultSet.getString("card_name"));
            System.out.println("Set: " + resultSet.getString("set"));
            System.out.println("Set Num: " + resultSet.getString("num"));
            System.out.println("Bought Price: " + resultSet.getString("bought_price"));
            System.out.println("Current Price: " + resultSet.getString("price"));
            System.out.println();
        }
    }
    public String[] getSetandNum(String name) throws SQLException {
        String[] setandNum = new String[2];
        String selectQuery = "SELECT * FROM cardinfo WHERE card_name = ?";
        preparedStatement = connection.prepareStatement(selectQuery);
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            setandNum[0] = resultSet.getString("set");
            setandNum[1] = resultSet.getString("num");
        }
        return setandNum;
    }

    public void deleteCard(Scanner reader) throws SQLException {
        String deleteQuery = "DELETE FROM cardinfo WHERE card_name=?;";
        preparedStatement = connection.prepareStatement(deleteQuery);
        System.out.print("Name of card: ");
        preparedStatement.setString(1, reader.nextLine());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void getPrices(Scanner reader) throws IOException, SQLException {
        String Scryfall_info = "failed";
        String ScryURL;
        OkHttpClient client = new OkHttpClient();

        System.out.print("Name of card to getPrices for: ");
        String name = reader.nextLine();
        String[] setandNum = getSetandNum(name);
        ScryURL = "https://api.scryfall.com/cards/" + setandNum[0] + "/" + setandNum[1];
        Request request = new Request.Builder().url(ScryURL).build();

        try (Response response = client.newCall(request).execute()) {
            assert response.body() != null;
            Scryfall_info = response.body().string();
        }

        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createJsonParser(Scryfall_info);

        while(parser.nextToken() != JsonToken.END_OBJECT) {
            parser.nextToken();
            try {
                if (parser.getCurrentName().equals("usd")) {
                    updateCurrentPrice(Double.parseDouble(parser.getText()), name);
                    break;
                }
            } catch (NullPointerException e) {
            }
        }
        parser.close();
    }
}
