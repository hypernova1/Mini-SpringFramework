package board.dao;

import board.dto.Post;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostDao {

    private static PostDao postDao = null;

    private JdbcUtil jdbcUtil = null;

    public PostDao() {
        this.jdbcUtil = JdbcUtil.getInstance();
    }

    public static PostDao getInstance() {
        if (postDao == null) postDao = new PostDao();
        return postDao;
    }

    public int insert(Post post) {
        List<String> params = new ArrayList<>();
        params.add(post.getTitle());
        params.add(post.getContent());
        return jdbcUtil.insert("INSERT INTO board (seq, title, content) VALUES (BOARD_SEQ.NEXTVAL, ?, ?)", params);
    }

    public List<Post> selectAll() {
        return jdbcUtil.selectAll("SELECT * FROM board");
    }

    public void update(Post post) {

    }

    public Post selectOne(int id) {
        return jdbcUtil.selectOne("SELECT * FROM board WHERE seq = ?", id);
    }
}
