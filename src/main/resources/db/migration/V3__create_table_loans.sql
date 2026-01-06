CREATE TABLE loans
(
    id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id      UUID        NOT NULL,
    book_id      UUID        NOT NULL,
    status       VARCHAR(20) NOT NULL,
    requested_at TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    loan_date    DATE,
    due_date     DATE,
    return_date  DATE,
    approved_by  UUID,

    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (book_id) REFERENCES books (id),
    FOREIGN KEY (approved_by) REFERENCES users (id)
);