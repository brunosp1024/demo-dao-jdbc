package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

	private Connection conn;

	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller obj) {

		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(
					"INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentId) " 
					+ "VALUES (?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS //permite retornar o Id gerado automaticamente no banco de dados.
			);
			
			ps.setString(1, obj.getName());
			ps.setString(2, obj.getEmail());
			ps.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			ps.setDouble(4, obj.getBaseSalary());
			ps.setInt(5, obj.getDepartment().getId());

			int rowsAffected = ps.executeUpdate(); //executa e armazena o número de linhas afetadas.
			
			if (rowsAffected > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				if(rs.next()) {
					obj.setId(rs.getInt(1));
				}
				DB.closeResultSet(rs);
			}else {
				throw new DbException("Unexpected error: no rows affected!");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(ps);
		}

	}

	@Override
	public void update(Seller obj) {
		
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(
					"UPDATE seller "
					+ "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
					+ "WHERE Id = ?"
			);
			
			ps.setString(1, obj.getName());
			ps.setString(2, obj.getEmail());
			ps.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			ps.setDouble(4, obj.getBaseSalary());
			ps.setInt(5, obj.getDepartment().getId());
			ps.setInt(6, obj.getId());

			ps.executeUpdate(); //executa e armazena o número de linhas afetadas.
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(ps);
		}

	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Seller findById(Integer id) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName " + "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id " + "WHERE seller.Id = ?");

			ps.setInt(1, id);
			rs = ps.executeQuery();

			if (rs.next()) { // Verifica se há algum resultado e retorna verdadeiro caso seja sim.
				Department dep = intantiateDepartment(rs);
				Seller sel = instantiateSeller(rs, dep);
				return sel;
			}

			return null;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(ps);
		}

	}

	@Override
	public List<Seller> findAll() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName  " + "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id " + "ORDER BY Name");

			rs = ps.executeQuery();
			Map<Integer, Department> map = new HashMap<>(); // Com o Map, o objeto Department não será instancido várias
															// vezes. // vezes, gerando repetições na memória;
			List<Seller> list = new ArrayList<Seller>();

			while (rs.next()) { // Verifica se há algum resultado e retorna verdadeiro caso seja sim.

				Department dep = map.get(rs.getInt("DepartmentId")); // Busca no Map se há algum Department com o Id
																		// informado.
				if (dep == null) {
					dep = intantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}

				Seller sel = instantiateSeller(rs, dep);
				list.add(sel);
			}

			return list;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(ps);
		}
	}

	@Override
	public List<Seller> findByDepartment(Department department) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName " + "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id " + "WHERE DepartmentId = ? " + "ORDER BY Name ");

			ps.setInt(1, department.getId());
			rs = ps.executeQuery();
			Map<Integer, Department> map = new HashMap<>(); // Com o Map, o objeto Department não será instancido várias
															// vezes. // vezes, gerando repetições na memória;
			List<Seller> list = new ArrayList<Seller>();

			while (rs.next()) { // Verifica se há algum resultado e retorna verdadeiro caso seja sim.

				Department dep = map.get(rs.getInt("DepartmentId")); // Busca no Map se há algum Department com o Id
																		// informado.
				if (dep == null) {
					dep = intantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}

				Seller sel = instantiateSeller(rs, dep);
				list.add(sel);
			}

			return list;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(ps);
		}
	}

	private Department intantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}

	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller sel = new Seller();
		sel.setId(rs.getInt("Id"));
		sel.setName(rs.getString("Name"));
		sel.setEmail(rs.getString("Email"));
		sel.setBirthDate(rs.getDate("BirthDate"));
		sel.setBaseSalary(rs.getDouble("BaseSalary"));
		sel.setDepartment(dep);
		return sel;
	}

}
