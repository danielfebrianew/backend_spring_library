-- V4__add_timestamps_to_users.sql
-- Add default values for created_at and updated_at columns in users table

ALTER TABLE users
    ALTER COLUMN created_at SET DEFAULT CURRENT_TIMESTAMP,
    ALTER COLUMN updated_at SET DEFAULT CURRENT_TIMESTAMP;