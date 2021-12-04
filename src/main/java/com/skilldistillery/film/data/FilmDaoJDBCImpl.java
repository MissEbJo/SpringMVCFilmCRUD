package com.skilldistillery.film.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.skilldistillery.film.entities.Actor;
import com.skilldistillery.film.entities.Film;

	@Repository
	public class FilmDaoJDBCImpl implements FilmDAO {
	//connection object goes here
		private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";
		private final String USER = "student";
		private final String PASS = "student";
		
//		private final String fullDataQuery = ""

		static {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		@Override
		public Film findFilmById(int filmId) {

			Film film = null;

			try {
				Connection conn = DriverManager.getConnection(URL, USER, PASS);
				String sql = "SELECT * FROM film \n"
						+ "JOIN language ON film.language_id = language.id \n"
						+ "JOIN film_category ON film.id = film_category.film_id\n"
						+ "JOIN category ON film_category.category_id = category.id\n"
						+ "WHERE film.id = ?";
				//add join for category section 
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setInt(1, filmId);
				ResultSet filmResult = stmt.executeQuery();

				if (filmResult.next()) {
					film = new Film();
					film.setId(filmResult.getInt("id"));
					film.setTitle(filmResult.getString("film.title"));
					film.setReleaseYear(filmResult.getString("release_year"));
					film.setDescription(filmResult.getString("description"));
					film.setRating(filmResult.getString("rating"));
//					add length
					film.setLength(filmResult.getInt("length"));
//					add special features
					film.setSpecialFeatures(filmResult.getString("special_features"));
//					category
					film.setCategory(filmResult.getString("category.name"));
					film.setLanguage(filmResult.getString("language.name"));
					film.setActors(findActorsByFilmId(filmId));

				}
				filmResult.close();
				stmt.close();
				conn.close();

			} catch (SQLException e) {
				System.out.println("Invalid input. Please try again.");
				e.printStackTrace();
			}
			return film;
		}

		@Override
		public Actor findActorById(int actorId) {
			Actor actor = null;
			try {
				Connection conn = DriverManager.getConnection(URL, USER, PASS);
				String sql = "SELECT id, first_name, last_name FROM actor WHERE id = ?";
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setInt(1, actorId);
				ResultSet actorResult = stmt.executeQuery();
				if (actorResult.next()) {
					actor = new Actor(); // Create the object
					// Here is our mapping of query columns to our object fields:
					actor.setId(actorResult.getInt("id"));
					actor.setFirstName(actorResult.getString("first_name"));
					actor.setLastName(actorResult.getString("last_name"));

				}
				actorResult.close();
				stmt.close();
				conn.close();
				return actor;
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		public List<Actor> findActorsByFilmId(int filmId) {

			List<Actor> actors = new ArrayList<>();
			Actor actor = null;
			String sql = "SELECT actor.first_name, actor.last_name ";
			sql += " FROM actor JOIN film_actor ON actor.id = film_actor.actor_id "
					+ "JOIN film ON film_actor.film_id = film.id " + " WHERE film.id = ?";
			try {
				Connection conn = DriverManager.getConnection(URL, USER, PASS);
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setInt(1, filmId);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					actor = new Actor();
					actor.setFirstName(rs.getString("actor.first_name"));
					actor.setLastName(rs.getString("actor.last_name"));

					actors.add(actor);
				}
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return actors;
		}

		public List<Film> findFilmsByKeyword(String theWordToSearchFor) {
			List<Film> films = new ArrayList<>();
			Film film = null;
			String sql = "SELECT * FROM film JOIN language ON film.language_id = language.id WHERE title LIKE ? OR description LIKE ?";

			try {
				Connection conn = DriverManager.getConnection(URL, USER, PASS);
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setString(1, "%" + theWordToSearchFor + "%");
				stmt.setString(2, "%" + theWordToSearchFor + "%");
				ResultSet filmResult = stmt.executeQuery();

				while (filmResult.next()) {
					film = new Film();
					film.setId(filmResult.getInt("film.id"));
					film.setTitle(filmResult.getString("film.title"));
					film.setReleaseYear(filmResult.getString("release_year"));
					film.setDescription(filmResult.getString("description"));
					film.setRating(filmResult.getString("rating"));
					film.setLanguage(filmResult.getString("language.name"));
					film.setActors(findActorsByFilmId(film.getId()));

					films.add(film);

				}

				filmResult.close();
				stmt.close();
				conn.close();

			} catch (SQLException e) {
				System.out.println("Invalid input. Please try again.");
				e.printStackTrace();
			}
			return films;

		}

		public Actor createActor(Actor actor) {
			Connection conn = null;
			try {
				conn = DriverManager.getConnection(URL, USER, PASS);
				conn.setAutoCommit(false); // START TRANSACTION
				String sql = "INSERT INTO actor (first_name, last_name) " + " VALUES (?,?)";

				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

				stmt.setString(1, actor.getFirstName());

				stmt.setString(2, actor.getLastName());

				int updateCount = stmt.executeUpdate();

				if (updateCount == 1) {
					ResultSet keys = stmt.getGeneratedKeys();
					if (keys.next()) {
						int newActorId = keys.getInt(1);

						actor.setId(newActorId);

						if (actor.getFilm() != null && actor.getFilm().size() > 0) {
							sql = "INSERT INTO film_actor (film_id, actor_id) VALUES (?,?)";

							stmt = conn.prepareStatement(sql);

							for (Film film : actor.getFilm()) {
								stmt.setInt(1, film.getId());
								stmt.setInt(2, newActorId);
								updateCount = stmt.executeUpdate();
							}
						}
					}
				} else {
					actor = null;
					conn.rollback();
				}

				conn.commit(); // COMMIT TRANSACTION

			} catch (SQLException sqle) {
				sqle.printStackTrace();
				if (conn != null) {
					try {
						conn.rollback();
					} catch (SQLException sqle2) {
						System.err.println("Error trying to rollback");
					}
				}
				throw new RuntimeException("Error inserting actor " + actor);
			}
			return actor;
		}

		public boolean updateActor(Actor actor) {
			Connection conn = null;
			try {
				conn = DriverManager.getConnection(URL, USER, PASS);
				conn.setAutoCommit(false); // START TRANSACTION
				String sql = "UPDATE actor SET first_name=?, last_name=? " + " WHERE id=?";
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setString(1, actor.getFirstName());
				stmt.setString(2, actor.getLastName());
				stmt.setInt(3, actor.getId());
				int updateCount = stmt.executeUpdate();
				if (updateCount == 1) {
					// Replace actor's film list
					sql = "DELETE FROM film_actor WHERE actor_id = ?";
					stmt = conn.prepareStatement(sql);
					stmt.setInt(1, actor.getId());
					updateCount = stmt.executeUpdate();
					sql = "INSERT INTO film_actor (film_id, actor_id) VALUES (?,?)";
					stmt = conn.prepareStatement(sql);
					for (Film film : actor.getFilm()) {
						stmt.setInt(1, film.getId());
						stmt.setInt(2, actor.getId());
						updateCount = stmt.executeUpdate();
					}
					conn.commit(); // COMMIT TRANSACTION
				}
			} catch (SQLException sqle) {
				sqle.printStackTrace();
				if (conn != null) {
					try {
						conn.rollback();
					} // ROLLBACK TRANSACTION ON ERROR
					catch (SQLException sqle2) {
						System.err.println("Error trying to rollback");
					}
				}
				return false;
			}
			return true;
		}
		public boolean deleteActor(Actor actor) {
			  Connection conn = null;
			  try {
			    conn = DriverManager.getConnection(URL, USER, PASS);
			    conn.setAutoCommit(false); // START TRANSACTION
			    String sql = "DELETE FROM film_actor WHERE actor_id = ?";
			    PreparedStatement stmt = conn.prepareStatement(sql);
			    stmt.setInt(1, actor.getId());
			    int updateCount = stmt.executeUpdate();
			    sql = "DELETE FROM actor WHERE id = ?";
			    stmt = conn.prepareStatement(sql);
			    stmt.setInt(1, actor.getId());
			    updateCount = stmt.executeUpdate();
			    conn.commit();             // COMMIT TRANSACTION
			  }
			  catch (SQLException sqle) {
			    sqle.printStackTrace();
			    if (conn != null) {
			      try { conn.rollback(); }
			      catch (SQLException sqle2) {
			        System.err.println("Error trying to rollback");
			      }
			    }
			    return false;
			  }
			  return true;
			}

		
		@Override
		public Film createFilm(Film newFilm) {
			Connection conn =null;
			try {
				conn = DriverManager.getConnection(URL, USER, PASS);
				conn.setAutoCommit(false); //Start Transaction
				String sql = "INSERT INTO film (title, description, language_id, release_year, rating) "
						+ "VALUES (?, ?, ?, ?, ?)";
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				
//				System.out.println(stmt + "*******");
				
				stmt.setString(1, newFilm.getTitle());
				
				stmt.setString(2, newFilm.getDescription());
				stmt.setInt(3, newFilm.getLanguageId());
				stmt.setString(4, newFilm.getReleaseYear());
				stmt.setString(5, newFilm.getRating());
				
//				System.out.println(stmt + "*******");
				
				int updateCount = stmt.executeUpdate();
				
				if(updateCount == 1) {
					ResultSet keys = stmt.getGeneratedKeys();
					if(keys.next()) {
						int newFilmId =keys.getInt(1);
						newFilm.setId(newFilmId);
//						
					}
				}else {
					newFilm =null;
					conn.rollback();
				}conn.commit();
				
				stmt.close();
				conn.close();
			}catch(SQLException e){
				e.printStackTrace();
		}
			
			return newFilm;
		}

		@Override
		public boolean deleteFilm(Film film) {
			Connection conn = null;
//			System.out.println(film.getId());
//			System.out.println("***********************");
			try {
				conn = DriverManager.getConnection(URL, USER, PASS);
				conn.setAutoCommit(false);
				String sql = "DELETE FROM film WHERE id = ?";
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setInt(1, film.getId());
				stmt.executeUpdate();
				conn.commit();
				
			}catch (SQLException e) {
				e.printStackTrace();
				
				return false;
			}
			return true;
		}

		
		@Override
		public Film updateDescriptionOfSpecificFilm(Film film, int filmId) {
			Connection conn = null;

			try {
				conn = DriverManager.getConnection(URL, USER, PASS);
				conn.setAutoCommit(false);
				String sql = "UPDATE film SET description = ? " + "WHERE id = ?";
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setString(1, film.getDescription());
				stmt.setInt(2, filmId);
				int updateCount = stmt.executeUpdate();
				conn.commit();

			} catch (SQLException sqle) {
				sqle.printStackTrace();
				if (conn != null) {
					try {
						conn.rollback();
					} catch (SQLException sqle2) {
						System.err.println("Error trying to rollback");
					}
				}
			}

			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return film;
		}

	}

