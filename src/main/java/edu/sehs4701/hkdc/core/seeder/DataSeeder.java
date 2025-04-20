package edu.sehs4701.hkdc.core.seeder;

import edu.sehs4701.hkdc.core.model.*;
import edu.sehs4701.hkdc.core.repository.*;
import edu.sehs4701.hkdc.core.type.Gender;
import edu.sehs4701.hkdc.core.type.Role;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    @NonNull
    private final UserRepository userRepository;

    @NonNull
    private final PatientRepository patientRepository;

    @NonNull
    private final ClinicRepository clinicRepository;

    @NonNull
    private final DentistRepository dentistRepository;

    @NonNull
    private final DentistScheduleRepository scheduleRepository;

    @NonNull
    private final DentalServiceRepository serviceRepository;

    @NonNull
    private final BookingRepository bookingRepository;

    @NonNull
    private final PasswordEncoder passwordEncoder;

    @NonNull
    private final AppointmentSlotRepository appointmentSlotRepository;

    @Override
    @Transactional
    public void run(String... args) {
        addAdmin();
        addClinics();
        addDentists();
        addDentalServices();
        addSchedules();
    }

    private void addAdmin() {
        String adminEmail = "admin@hkdc.com";
        if (userRepository.findByEmail(adminEmail).isEmpty()) {
            User admin = new User();
            admin.setFirstName("Admin");
            admin.setLastName("User");
            admin.setEmail(adminEmail);
            admin.setPhone("0000000000");
            admin.setPassword(passwordEncoder.encode("Test@123"));
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);
        }
    }

    private void addClinics() {
        if (clinicRepository.count() == 0) {
            clinicRepository.saveAll(List.of(
                    createClinic("Central Clinic", "123 Queen’s Road Central, Central", "Mon-Fri 9am-6pm"),
                    createClinic("TST Clinic", "88 Nathan Road, Tsim Sha Tsui", "Mon-Sat 10am-7pm"),
                    createClinic("Kwai Chung Clinic", "55 Hing Fong Road, Kwai Chung", "Tue-Sun 8am-5pm"),
                    createClinic("Sha Tin Clinic", "22 Yuen Wo Road, Sha Tin", "Mon-Fri 9am-6pm"),
                    createClinic("Causeway Bay Clinic", "18 Great George Street, Causeway Bay", "Mon-Sat 10am-8pm")
            ));
        }
    }

    private Clinic createClinic(String name, String address, String hours) {
        Clinic clinic = new Clinic();
        clinic.setName(name);
        clinic.setAddress(address);
        clinic.setOpenHours(hours);
        return clinic;
    }

    private void addDentists() {
        if (dentistRepository.count() == 0) {
            dentistRepository.saveAll(List.of(
                    createDentist("William", "Lam", Gender.MALE, "93338888", "william@hkdc.com"),
                    createDentist("Amy", "Chan", Gender.FEMALE, "95556666", "amy@hkdc.com"),
                    createDentist("David", "Wong", Gender.MALE, "91112222", "david@hkdc.com")
            ));
        }
    }

    private Dentist createDentist(String first, String last, Gender gender, String phone, String email) {
        Dentist d = new Dentist();
        d.setFirstName(first);
        d.setLastName(last);
        d.setGender(gender);
        d.setPhonenum(phone);
        d.setEmail(email);
        return d;
    }

    private void addDentalServices() {
        if (serviceRepository.count() == 0) {
            serviceRepository.saveAll(List.of(
                    createService("Teeth Cleaning", new BigDecimal("500.00")),
                    createService("Root Canal", new BigDecimal("1200.00")),
                    createService("Tooth Extraction", new BigDecimal("800.00")),
                    createService("Braces", new BigDecimal("3000.00"))
            ));
        }
    }

    private DentalService createService(String name, BigDecimal charges) {
        DentalService ds = new DentalService();
        ds.setName(name);
        ds.setCharges(charges);
        return ds;
    }

    private void addSchedules() {
        if (scheduleRepository.count() == 0) {
            Clinic tst = clinicRepository.findByName("TST Clinic").orElseThrow();
            Clinic kwai = clinicRepository.findByName("Kwai Chung Clinic").orElseThrow();
            Dentist lam = dentistRepository.findAll().stream()
                    .filter(d -> d.getLastName().equals("Lam")).findFirst().orElseThrow();

            List<DentistSchedule> schedules = List.of(
                    createSchedule(lam, tst, DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(10, 0)),
                    createSchedule(lam, tst, DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(10, 0)),
                    createSchedule(lam, kwai, DayOfWeek.TUESDAY, LocalTime.of(15, 0), LocalTime.of(16, 0))
            );

            scheduleRepository.saveAll(schedules);

            for (DentistSchedule schedule : schedules) {
                List<AppointmentSlot> generatedSlots = generateSlotsFromSchedule(schedule, 14);
                schedule.setSlots(generatedSlots);
                scheduleRepository.save(schedule);
            }
        }
    }

    private List<AppointmentSlot> generateSlotsFromSchedule(DentistSchedule schedule, int daysAhead) {
        List<AppointmentSlot> slots = new ArrayList<>();
        for (int i = 0; i < daysAhead; i++) {
            LocalDate date = LocalDate.now().plusDays(i);
            if (date.getDayOfWeek() != schedule.getDayOfWeek()) continue;

            LocalTime time = schedule.getStartTime();
            while (!time.plusHours(1).isAfter(schedule.getEndTime())) {
                LocalTime end = time.plusHours(1);

                AppointmentSlot slot = new AppointmentSlot();
                slot.setDate(date);
                slot.setStartTime(time);
                slot.setEndTime(end);
                slot.setDentistSchedule(schedule);
                slot.setIsBooked(false);

                slots.add(slot);
                time = end;
            }
        }
        appointmentSlotRepository.saveAll(slots);
        return slots;
    }

    private DentistSchedule createSchedule(Dentist d, Clinic c, DayOfWeek dow, LocalTime start, LocalTime end) {
        DentistSchedule s = new DentistSchedule();
        s.setDentist(d);
        s.setClinic(c);
        s.setDayOfWeek(dow);
        s.setStartTime(start);
        s.setEndTime(end);
        return s;
    }



}
