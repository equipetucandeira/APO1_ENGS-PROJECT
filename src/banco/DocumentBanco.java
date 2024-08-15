package banco;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import model.Document;

public class DocumentBanco {

	public static int InsertPathAndSendNotification(String filePath, String fileName) throws SQLException {
		int documentid = -1;
		
			DBConnection connection = new DBConnection();
			String sql = "call taskDocumentInsert(?,?,?) ";
			CallableStatement statement = connection.getConnection().prepareCall(sql);
			statement.setString(1, filePath);
			statement.setString(2, fileName);
			statement.registerOutParameter(3, Types.INTEGER);
			statement.executeUpdate();
			documentid = statement.getInt(3);
			statement.close();

		return documentid;
	}
	
	public static Document getDocument(Integer id) throws SQLException {

			DBConnection connection = new DBConnection();
			String sql = "SELECT * from documents where document_id = ?";
			PreparedStatement statement = connection.getConnection().prepareStatement(sql);
		
			statement.setInt(1,id);
			
			ResultSet rs = statement.executeQuery();
			if(rs.next()) {
				Document document = new Document(rs.getString("document_title"),rs.getString("document_path"));
				return document;
			}
			rs.close();
			statement.close();
			return null;
	
	}

}
