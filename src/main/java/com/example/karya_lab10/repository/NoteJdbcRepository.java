package com.example.karya_lab10.repository;

import com.example.karya_lab10.model.Note;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class NoteJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public NoteJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 🔥 RAW SQL + PREPARED STATEMENT (PDF ZORUNLULUĞU)
    public List<Note> findNotesByUserIdRaw(Long userId) {
        String sql = "SELECT id, title, content, user_id FROM note WHERE user_id = ?";

        return jdbcTemplate.query(sql, new Object[]{userId}, new NoteRowMapper());
    }

    private static class NoteRowMapper implements RowMapper<Note> {
        @Override
        public Note mapRow(ResultSet rs, int rowNum) throws SQLException {
            Note note = new Note();
            note.setId(rs.getLong("id"));
            note.setTitle(rs.getString("title"));
            note.setContent(rs.getString("content"));
            note.setUserId(rs.getLong("user_id"));
            return note;
        }
    }
}
