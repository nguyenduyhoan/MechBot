package hoannd.mech.bot.MechBot.repository;

import hoannd.mech.bot.MechBot.model.Role;
import hoannd.mech.bot.MechBot.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}

