
TRUNCATE TABLE Movie, Review, movie_genre, movie_details,Genre,Award;

insert into Movie (name,certification, id) values ('tintin',2, -2);

insert into Review (author, content, movie_id, id) values ('max', 'plutôt ok', -2, -3);
insert into Review (author, content, movie_id, id) values ('max', 'ça va', -2, -4);
insert into Review (author, content, movie_id, id) values ('ernest', 'super', -2, -5);
insert into Review (author, content, movie_id, id) values ('jp', 'moyen!', -2, -6);

