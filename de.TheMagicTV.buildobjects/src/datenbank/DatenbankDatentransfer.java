package datenbank;

import java.sql.ResultSet;
import java.sql.SQLException;


public class DatenbankDatentransfer {
	
	public boolean existProject(String name) {
		try {
			java.sql.PreparedStatement ps = DatenbankVerbindung.getConnection().prepareStatement("SELECT * FROM building WHERE name = ?");
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean addProject(String name) {
		try {
			java.sql.PreparedStatement ps = DatenbankVerbindung.getConnection().prepareStatement("INSERT INTO building (name) VALUES (?)");
			ps.setString(1, name);
			ps.executeUpdate();
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean removeProject(String name) {
		try {
			java.sql.PreparedStatement ps = DatenbankVerbindung.getConnection().prepareStatement("DELETE FROM building WHERE name = ?");
			ps.setString(1, name);
			ps.executeUpdate();
			
			java.sql.PreparedStatement ps1 = DatenbankVerbindung.getConnection().prepareStatement("DELETE FROM blocks WHERE building = ?");
			ps1.setString(1, name);
			ps1.executeUpdate();
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	
	public boolean existBlockInProject(String weltname, int x, int y, int z, String building) {
		try {
			java.sql.PreparedStatement ps = DatenbankVerbindung.getConnection().prepareStatement("SELECT * FROM blocks WHERE weltname = ? AND "
					+ "x = ? AND y = ? AND z = ? AND building = ?");
			ps.setString(1, weltname);
			ps.setInt(2, x);
			ps.setInt(3, y);
			ps.setInt(4, z);
			ps.setString(5, building);
			ResultSet rs = ps.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean addBlock(String blockName, String weltname, int x, int y, int z, String building) {
		try {
			java.sql.PreparedStatement ps = DatenbankVerbindung.getConnection().prepareStatement("INSERT INTO blocks (block,weltname,x,y,z,building) VALUES (?,?,?,?,?,?)");
			ps.setString(1, blockName);
			ps.setString(2, weltname);
			ps.setInt(3, x);
			ps.setInt(4, y);
			ps.setInt(5, z);
			ps.setString(6, building);
			ps.executeUpdate();
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean removeBlock(String blockName, String weltname, int x, int y, int z, String building) {
		try {
			java.sql.PreparedStatement ps = DatenbankVerbindung.getConnection().prepareStatement("DELETE FROM blocks WHERE block = ? AND weltname = ? AND x = ? AND y = ? AND z = ? AND building = ?");
			ps.setString(1, blockName);
			ps.setString(2, weltname);
			ps.setInt(3, x);
			ps.setInt(4, y);
			ps.setInt(5, z);
			ps.setString(6, building);
			ps.executeUpdate();
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean changeBlock(String blockName, String weltname, int x, int y, int z, String building) {
		try {
			java.sql.PreparedStatement ps = DatenbankVerbindung.getConnection().prepareStatement("UPDATE blocks SET block = ? WHERE weltname = ? AND "
					+ "x = ? AND y = ? AND z = ? AND building = ?");
			ps.setString(1, blockName);
			ps.setString(2, weltname);
			ps.setInt(3, x);
			ps.setInt(4, y);
			ps.setInt(5, z);
			ps.setString(6, building);
			ps.executeUpdate();
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean updateAllLocationsOfBlocksInProject(String projektname, int pX, int pY, int pZ) {
		try {
			java.sql.PreparedStatement ps = DatenbankVerbindung.getConnection().prepareStatement("SELECT * FROM blocks WHERE building = ?");
			ps.setString(1, projektname);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int rsX = rs.getInt("x");
				int rsY= rs.getInt("y");
				int rsZ = rs.getInt("z");
				System.out.println("x:" + rsX + "    y:" + rsY + "   z:" + rsZ);
				System.out.println("px:" + pX + "    py:" + pY + "   pz:" + pZ);
				int x = (rsX - pX);
				int y = (rsY - pY);
				int z = (rsZ - pZ);
				try {
					java.sql.PreparedStatement psOne = DatenbankVerbindung.getConnection().prepareStatement("UPDATE blocks SET x = ?,y = ?, z = ? WHERE weltname = ? AND "
							+ "x = ? AND y = ? AND z = ? AND building = ?");
					psOne.setInt(1, x);
					psOne.setInt(2, y);
					psOne.setInt(3, z);
					psOne.setString(4, rs.getString("weltname"));
					psOne.setInt(5, rsX);
					psOne.setInt(6, rsY);
					psOne.setInt(7, rsZ);
					psOne.setString(8, rs.getString("building"));
					psOne.executeUpdate();
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
