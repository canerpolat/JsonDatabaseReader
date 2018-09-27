package com.caner.database;

import java.sql.SQLException;
import com.google.gson.JsonObject;

public class Main {

	public static void main(String[] args) throws SQLException {
		JsonReaderForDatabase reader = new JsonReaderForDatabase();
		JsonObject jsonObject = reader.jsonReader("C:\\deneme/rehber.json");

		String encode = reader.encode(jsonObject);
		String decode = reader.decode(encode);
		RehberEntity dbInsert = reader.rehberEntity(decode);
		reader.jsonInsertEntityDatabase(dbInsert);
		reader.jsonUpdateEntityDatabase(dbInsert);
		reader.jsonDeleteEntityDatabase(dbInsert);
		reader.jsonSelectEntityDatabase(dbInsert);

	}
}