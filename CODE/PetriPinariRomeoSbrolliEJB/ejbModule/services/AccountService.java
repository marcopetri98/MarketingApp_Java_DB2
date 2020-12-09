package services;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import database.User;
import exceptions.UserAlreadyPresentNicknameException;
import exceptions.UserCredentialsException;
import exceptions.UserException;

@Stateless
public class AccountService {
	@PersistenceContext(unitName = "PetriPinariRomeoSbrolliEJB")
	private EntityManager em;
	
	/**
	 * 
	 * @param nickname
	 * @param password
	 * @param email
	 * @param regDate
	 */
	public void createNewUser(String nickname, String password, String email, Date regDate) throws UserException {
		List<User> users = em.createQuery("User.findByName", User.class).setParameter(1, nickname).getResultList();
		User user;
		
		// the user is not unique, precisely the user exist
		if (!users.isEmpty()) {
			throw new UserException();
		} else {
			user = new User(nickname,password,email,regDate,0,false,0);
			em.persist(user);
		}
	}
	
	/**
	 * If finds a user given its id, which is its primary key.
	 * @param userId the user id of the user
	 * @return the user object if the user exists, if not it returns NULL
	 */
	public User findUser(int userId) {
		User user = em.find(User.class, userId);
		return user;
	}
	
	/**
	 * This method verifies if the pair is correct and returns true if it is. If the return value is true the user can login, if it false it cannot login.
	 * @param nickname is the nickname of the user
	 * @param password is the password given by the user
	 * @return true if the pair matches on the database, false otherwise
	 */
	public boolean login(String nickname, String password) {
		List<User> users = em.createQuery("User.findByName", User.class).setParameter(1, nickname).getResultList();
		
		if (users.size() != 1) {
			return users.get(0).getPassword().equals(password);
		} else {
			return false;
		}
	}
	
	/**
	 * It changes the nickname of a user if it inserts a correct password and the nickname is available.
	 * @param id
	 * @param newNickname
	 * @param password
	 * 
	 */
	public void changeNickname(int id, String newNickname, String password) throws UserCredentialsException, UserAlreadyPresentNicknameException {
		User changingNicknameUser = em.find(User.class, id);
		List<User> nicknameUser = em.createQuery("User.findByName", User.class).setParameter(1, newNickname).getResultList();
		
		// verify that the user is inserting the right password in order to make the change possible by vertical propagation
		if (login(changingNicknameUser.getNickname(), password)) {
			if (nicknameUser != null) {
				throw new UserAlreadyPresentNicknameException();
			} else {
				changingNicknameUser.setNickname(newNickname);
			}
		} else {
			throw new UserCredentialsException();
		}
	}
}