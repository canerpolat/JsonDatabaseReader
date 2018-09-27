package com.caner.database;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonReaderForDatabase {
	JsonObject jsonObject = null;
	JsonParser parser = new JsonParser();
	Object object = null;
	Connection getConnect = null;
	PreparedStatement preparedStatement = null;
	Statement statement = null;
	ConnectionDatabase connection = new ConnectionDatabase();

	public JsonObject jsonReader(String url) {

		try {
			object = parser.parse(new FileReader(url));
			jsonObject = (JsonObject) object;

		} catch (Exception e) {
			System.err.println(e.toString());
		}
		return jsonObject;
	}

	public String encode(JsonObject jsonObject) {
		byte[] encodedBytes = Base64.getEncoder().encode(jsonObject.toString().getBytes());
		return new String(encodedBytes);

	}

	public String decode(String encodedBytes) {
		byte[] decodeBytes = Base64.getDecoder().decode(encodedBytes);
		return new String(decodeBytes);

	}

	public RehberEntity rehberEntity(String decode) {
		RehberEntity rehberEntity = new RehberEntity();
		object = parser.parse(decode);
		jsonObject = (JsonObject) object; // object i json tipinde kullanabilmek için cast et.
		// getAsString öðeyi dize deðeri olarak al
		rehberEntity.setId(jsonObject.get("ID").getAsString());
		rehberEntity.setName(jsonObject.get("Adi").getAsString());
		rehberEntity.setSurname(jsonObject.get("Soyadi").getAsString());
		rehberEntity.setPhone(jsonObject.get("Telefon").getAsString());
		rehberEntity.setMail(jsonObject.get("Email").getAsString());
		rehberEntity.setSehir(jsonObject.get("Sehir").getAsString());
		return rehberEntity;
		// rehberEntity jsonData set ederek doldurdum
	}

	public void jsonInsertEntityDatabase(RehberEntity rehberEntity) throws SQLException {
		// Auto increment yaparak id yi otomatik atýlýr.

		getConnect = connection.getConnection("Ogrenci_db", "root", "");

		try {
			String sqlInsert = "Insert into rehber(name,surname,phone,mail,sehir) VALUES(?,?,?,?,?)";
			PreparedStatement statement = getConnect.prepareStatement(sqlInsert);

			statement.setString(1, rehberEntity.getName());
			statement.setString(2, rehberEntity.getSurname());
			statement.setString(3, rehberEntity.getPhone());
			statement.setString(4, rehberEntity.getMail());
			statement.setString(5, rehberEntity.getSehir());

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0)
				System.out.println("insert eklendi");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getConnect.close();
		}

	}

	public void jsonUpdateEntityDatabase(RehberEntity rehberEntity) throws SQLException {

		getConnect = connection.getConnection("Ogrenci_db", "root", "");
		try {
			System.out.println("in update");
			String sqlUpdate = "UPDATE rehber SET name = ?, surname = ? WHERE name = ?";
			preparedStatement = getConnect.prepareStatement(sqlUpdate);

			preparedStatement.setString(1, "atif");
			preparedStatement.setString(2, "sesu");
			preparedStatement.setString(3, "Hasan");

			int rowsUptated = preparedStatement.executeUpdate();
			if (rowsUptated > 0)
				System.out.println("Update baþarýlý");
		} catch (Exception e) {
			System.err.println("Update hata : " + e);
		} finally {
			getConnect.close();
		}
	}

	public void jsonDeleteEntityDatabase(RehberEntity rehberEntity) throws SQLException {
		getConnect = connection.getConnection("Ogrenci_db", "root", "");
		try {
			System.out.println("in Delete ");
			String sqlDelete = "DELETE FROM rehber WHERE id = ?";
			PreparedStatement statement = getConnect.prepareStatement(sqlDelete);
			statement.setInt(1, 42);
			int rowsDeleted = statement.executeUpdate();
			if (rowsDeleted > 0)
				System.out.println("Delete iþlemi baþarýlý");
		} catch (Exception e) {
			System.err.println(e);
		} finally {
			getConnect.close();
		}

	}

	public void jsonSelectEntityDatabase(RehberEntity rehberEntity) throws SQLException {

		getConnect = connection.getConnection("Ogrenci_db", "root", "");
		try {
			System.out.println("Creating statement...");
			statement = getConnect.createStatement();
			String sqlSelect = "Select id,name,surname,phone,mail,sehir FROM rehber";
			ResultSet resultSet = statement.executeQuery(sqlSelect);
			while (resultSet.next()) {

				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String surname = resultSet.getString("surname");
				String phone = resultSet.getString("phone");
				String mail = resultSet.getString("mail");
				String sehir = resultSet.getString("sehir");

				System.out.println("id : " + id);
				System.out.println("isim : " + name);
				System.out.println("SoyÝsim : " + surname);
				System.out.println("Telefon : " + phone);
				System.out.println("Mail : " + mail);
				System.out.println("Þehir : " + sehir);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getConnect.close();
		}

	}
}