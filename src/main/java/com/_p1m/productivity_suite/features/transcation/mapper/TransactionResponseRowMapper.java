package com._p1m.productivity_suite.features.transcation.mapper;

import com._p1m.productivity_suite.features.transcation.dto.TransactionResponse;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionResponseRowMapper implements RowMapper<TransactionResponse> {

    @Override
    public TransactionResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new TransactionResponse(
                rs.getLong("id"),
                rs.getBigDecimal("amount"),
                rs.getString("description"),
                rs.getLong("transaction_date"),
                rs.getLong("created_at"),
                rs.getLong("updated_at")
        );
    }
}
