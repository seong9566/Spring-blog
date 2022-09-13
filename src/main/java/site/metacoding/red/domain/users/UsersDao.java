package site.metacoding.red.domain.users;

import java.util.List;

import site.metacoding.red.web.request.users.LoginDto;


public interface UsersDao {
	public void join(Users users);
	public List<Users> findAll();
	public Users findById(Integer id);
	public void update(Users users);
	public void deleteById(Integer id);
	public Users findByUsername(String username);
}
