package hifive.hifivespring.repository;

import hifive.hifivespring.domain.Member;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcMemberRepository implements MemberRepository {

    private final DataSource dataSource;

    //spring에게서 주입받아야함.
    public JdbcMemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;

    }


    @Override
    public Member save(Member member) {

        String sql = "insert into member(name) values(?)";

       /* Connection conn = dataSource.getConnection();

        PreparedStatement pstmt = connection.prepareStatement(sql);

        pstmt.setString(1, member.getName());

        pstmt.executeUpdate();

        return null;*/

        Connection conn = null;
        PreparedStatement pstmt = null;
        //결과를 받는 것
        ResultSet rs = null;

        try {
            conn = getConnection(); //connection을 가져오고
            // connection에서 prepareStatement에서 sql를 넣고 RETURN_GENERATED_KEYS라는 특별한 옵션이 있다. (자동으로 증가됐던)
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //setString에서 파라미터 인덱스 1번은 위 values의 ?,member.getName()의 값을 넣는다.
            pstmt.setString(1, member.getName());

            //pstmt에다가 executeUpdate하면 DB의 실제 insert 쿼리문이 날라간다.
            pstmt.executeUpdate();

            //그다음에 getGeneratedKeys()를 꺼낼 수 있는데 꺼낸 키를 반환해준다.
            rs = pstmt.getGeneratedKeys();

            //resultSet에서 값이 있으면 값을 꺼내면 된다.
            if (rs.next()) {

                //조회 성공하면 값을 꺼내고 세팅해주면된다.
                member.setId(rs.getLong(1));
            } else {

                //실패하면 조회 실패.
                throw new SQLException("id 조회 실패");
            }
            return member;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {

            close(conn, pstmt, rs);
        }
    }

    //조회
    @Override
    public Optional<Member> findById(Long id) {
        String sql = "select * from member where id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            //executeQuery()로 값을 받아와서
            if (rs.next()) {
                //값이 있으면  멤버객체를 쭉 만들고 반환해준다.
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    //목록
    @Override
    public List<Member> findAll() {
        String sql = "select * from member";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            List<Member> members = new ArrayList<>();

            while (rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                members.add(member);
            }
            return members;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<Member> findByName(String name) {
        String sql = "select * from member where name = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}