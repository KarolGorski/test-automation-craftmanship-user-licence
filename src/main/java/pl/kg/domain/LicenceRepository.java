package pl.kg.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface LicenceRepository extends JpaRepository<Licence, Long> {
}
