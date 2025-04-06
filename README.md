# PAO2025

# Dog and Cat Shelter 

I'll be making a java project that handles the operations of an animal shelter. 

Users (Staff Members) will be able to do the following actions:

- Get a list of all the animals rescued in a specific shelter
- Get a list of all the animals that aren't adopted yet
- Get a list of all the employees in a specific shelter
- Get a list of all the people who have rescued animals
- Get a list of all appointments in a specific shelter
- Add a new person or staff member
- Assign a pet to a staff member
- Add a new rescue
- Add a new adoption
- Add a new appointment
  
```mermaid
erDiagram
    PERSON ||--o{ APPOINTMENT : IsAppointed
    SHELTER ||--o{ APPOINTMENT : HasAppointment

    PERSON ||--o{ ADOPTION : Adopts
    ANIMAL ||--o| ADOPTION : IsAdopted

    PERSON ||--o{ RESCUE : Rescues
    ANIMAL ||--o| RESCUE : IsRescued

    STAFF ||--o{ PAYMENT : IsPaid

    STAFF ||--|| PERSON : IsA
    STAFF ||--o{ ANIMAL : TakesCare

    SHELTER }|--|| STAFF : Works
    SHELTER ||--o{ ANIMAL : Shelters  

    ANIMAL ||--o{ TRANSFER : IsTransfered
    SHELTER ||--o{ TRANSFER : Transfered

    PERSON {
      ulong person_id pk
      varchar firstname
      varchar lastname
      varchar email
      varchar password
    }
    ANIMAL {
      ulong animal_id pk
      varchar name
      varchar description
      enum species
    }
    STAFF {
      ulong staff_id pk 
      ulong person_id fk
      datetime employment_date
      int salary
    }
    RESCUE {
      ulong rescue_id pk 
      ulong person_id fk
      ulong animal_id fk
      datetime rescue_date
    }
    ADOPTION {
      ulong adoption_id pk
      ulong person_id fk
      ulong animal_id fk 
      datetime adoption_date
    }
    SHELTER {
      ulong shelter_id pk
      varchar name 
      varchar address
    }
    PAYMENT {
      ulong payment_id pk
      ulong staff_id fk 
      number amount
      datetime date 
    }
    APPOINTMENT {
      ulong appointment_id pk 
      ulong shelter_id fk
      ulong person_id fk
      datetime date 
    }
    
    TRANSFER {
      ulong transfer_id pk
      ulong animal_id fk 
      ulong from_shelter fk
      ulong to_shelter fk
      datetime date 
    }  
```
