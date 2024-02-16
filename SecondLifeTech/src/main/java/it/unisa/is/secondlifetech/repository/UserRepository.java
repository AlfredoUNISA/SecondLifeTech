package it.unisa.is.secondlifetech.repository;

import it.unisa.is.secondlifetech.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
	// TODO: creare findByEmail
}
