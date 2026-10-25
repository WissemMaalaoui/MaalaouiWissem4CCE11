package tn.esprit.maalaouiwissem4cce11.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.maalaouiwissem4cce11.domain.Reservation;
import tn.esprit.maalaouiwissem4cce11.exception.ResourceNotFoundException;
import tn.esprit.maalaouiwissem4cce11.repository.IReservationRepository;
import tn.esprit.maalaouiwissem4cce11.service.IReservationService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {
    private final IReservationRepository reservationRepository;
    @Override
    public Reservation create(Reservation reservation) {
        if (reservation.getIdReservation() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau reservation ne doit pas avoir d'identifiant");
        }
        return reservationRepository.save(reservation);
    }
    @Override
    public Reservation update(Long id,Reservation reservation) {
        Reservation existant = findById(id);
        existant.setDateDebut(reservation.getDateDebut());
        existant.setDateFin(reservation.getDateFin());
        return reservationRepository.save(existant);
    }
    @Override
    public void deleteById(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reservation", id);
        }
        reservationRepository.deleteById(id);
    }
    @Override
    public Reservation findById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation",id));
    }
    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }
}