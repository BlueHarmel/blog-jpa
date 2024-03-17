INSERT INTO article (title, content, created_at, updated_at) VALUES ('침하하', '독깨팔 귀여워', NOW(), NOW());
INSERT INTO article (title, content, created_at, updated_at) VALUES ('제목2', '내용2', NOW(), NOW());
INSERT INTO article (title, content, created_at, updated_at) VALUES ('제목3', '내용3', NOW(), NOW());

INSERT INTO comment (article_id, created_at, body) VALUES (1, NOW(),'댓글 내용1');
INSERT INTO comment (article_id, created_at, body) VALUES (1, NOW(),'댓글 내용2');
INSERT INTO comment (article_id, created_at, body) VALUES (2, NOW(),'댓글 내용3');