package net.javaguides.__backend.repository;

import net.javaguides.__backend.entity.Offering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OfferingRepository extends JpaRepository<Offering, Long> {

}
