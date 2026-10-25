package tn.esprit.maalaouiwissem4cce11.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.esprit.maalaouiwissem4cce11.domain.Maintenance;

public interface IMaintenanceRepository extends JpaRepository<Maintenance,Long> {
}
