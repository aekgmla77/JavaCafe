package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmpDAO {
	Connection conn = null;

	public EmpDAO() {
		try {
			String user = "hr";
			String pw = "hr";
			String url = "jdbc:oracle:thin:@localhost:1521:xe";

			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, user, pw);

			System.out.println("Database에 연결되었습니다.\n");

		} catch (ClassNotFoundException cnfe) {
			System.out.println("DB 드라이버 로딩 실패 :" + cnfe.toString());
		} catch (SQLException sqle) {
			System.out.println("DB 접속실패 : " + sqle.toString());
		} catch (Exception e) {
			System.out.println("Unkonwn error");
			e.printStackTrace();
		}
	} //end of 생성자
	
	public void insertSchedule(Schedule sch) {
		String sql = "insert into calendar values(?,?,?,?)";
		try {
			PreparedStatement psmt = conn.prepareStatement(sql);
			psmt.setString(1, sch.getTitle());
			psmt.setString(2, sch.getStartDay());
			psmt.setString(3, sch.getEndDay());
			psmt.setString(4, sch.getUrl());
			int r = psmt.executeUpdate();
			System.out.println(r + "건 입력되었습니다.");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<Schedule> getScheduleList() {
		String sql = "select * from calendar";
		List<Schedule> list = new ArrayList<>();
		try {
			PreparedStatement psmt = conn.prepareStatement(sql);
			ResultSet rs = psmt.executeQuery();
			while(rs.next()) {
				Schedule schedule = new Schedule();
				schedule.setTitle(rs.getString("title"));
				schedule.setStartDate(rs.getString("start_day"));
				schedule.setEndDate(rs.getString("end_day"));
				schedule.setUrl(rs.getString("url"));
				list.add(schedule);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	public Map<String, Integer> getMemberByDept(){
		String sql = "select department_name, count(*) "
				+ "from employees e, departments d "
				+ "where e.department_id = d.department_id "
				+ "group by department_name";
		
		Map<String, Integer> map = new HashMap<>();
		
		try {
			PreparedStatement psmt = conn.prepareStatement(sql);
			ResultSet rs = psmt.executeQuery();
		    while(rs.next()) {
		    	map.put(rs.getString(1), rs.getInt(2));
		    }
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return map;
	}
	
	public EmployeeVO updateEmp(EmployeeVO vo) {
		String sql = "update emp_temp set first_name = ?, "
				+ "last_name = ?, email = ?, phone_number = ?, "
				+ "hire_date = TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss'),\r\n"
				+ "job_id = ?, salary = ?  where employee_id = ?";
		int r = 0;
		try {
			PreparedStatement psmt = conn.prepareStatement(sql);
			psmt.setString(1, vo.getFirstName());
			psmt.setString(2, vo.getLastName());
			psmt.setString(3, vo.getEmail());
			psmt.setString(4, vo.getPhoneNumber());
			psmt.setString(5, vo.getHireDate());
			psmt.setString(6, vo.getJobId());
			psmt.setInt(7, vo.getSalary());
			psmt.setInt(8, vo.getEmployeeId());
			
			r = psmt.executeUpdate();
			System.out.println(r + "건 수정되었습니다.");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return vo;
	}
	
	public EmployeeVO insertEmp(EmployeeVO vo) {
		String sql1 = "select employees_seq.nextval from dual";
		String sql2 = "select * from emp_temp where employee_id = ?";
		String sql = "insert into emp_temp(employee_id, first_name, last_name, email, phone_number, hire_date, job_id, salary)\r\n"
				+ "values(?, ?, ?, ?, ?, TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss'), ?, ?)";
		int r = 0;
		String newSeq = null;
		EmployeeVO newVo = new EmployeeVO();
		try {
			PreparedStatement psmt = conn.prepareStatement(sql1);
			ResultSet rs = psmt.executeQuery();
			if(rs.next()) {
				newSeq = rs.getString(1);
			}
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, newSeq);
			psmt.setString(2, vo.getFirstName());
			psmt.setString(3, vo.getLastName());
			psmt.setString(4, vo.getEmail());
			psmt.setString(5, vo.getPhoneNumber());
			psmt.setString(6, vo.getHireDate());
			psmt.setString(7, vo.getJobId());
			psmt.setInt(8, vo.getSalary());
			r = psmt.executeUpdate();
			System.out.println(r + "건 입력되었습니다.");
			
			psmt = conn.prepareStatement(sql2);
			psmt.setString(1, newSeq);
			rs = psmt.executeQuery();
			if(rs.next()) {
				newVo.setEmail(rs.getString("email"));
				newVo.setEmployeeId(rs.getInt("employee_id"));
				newVo.setFirstName(rs.getString("first_name"));
				newVo.setLastName(rs.getString("last_name"));
				newVo.setHireDate(rs.getString("hire_date"));
				newVo.setJobId(rs.getString("job_id"));
				newVo.setSalary(rs.getInt("salary"));
				newVo.setPhoneNumber(rs.getString("phone_number"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return newVo;
	}
	
	public boolean deleteEmp(EmployeeVO vo) {
		String sql = "delete from emp_temp where employee_id = ?";
		int r = 0;
		try {
			PreparedStatement psmt = conn.prepareStatement(sql);
			psmt.setInt(1, vo.getEmployeeId());
			
			r = psmt.executeUpdate();
			System.out.println(r + "건 삭제되었습니다.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r == 1 ? true : false;
	}
	
	public List<EmployeeVO> getEmpList() {
		String sql = "select * from emp_temp order by 1 desc";
		List<EmployeeVO> list = new ArrayList<>();
		
		try {
			PreparedStatement psmt = conn.prepareStatement(sql);
			ResultSet rs = psmt.executeQuery();
			while(rs.next()) {
				EmployeeVO vo = new EmployeeVO();
				vo.setEmployeeId(rs.getInt("employee_id"));
				vo.setFirstName(rs.getString("first_name"));
				vo.setLastName(rs.getString("last_name"));
				vo.setPhoneNumber(rs.getString("phone_number"));
				vo.setEmail(rs.getString("email"));
				vo.setHireDate(rs.getString("hire_date"));
				vo.setJobId(rs.getString("job_id"));
				vo.setSalary(rs.getInt("salary"));
				
				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}
