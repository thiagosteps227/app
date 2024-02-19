package com.booking.app.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "property")
public class PropertyModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private String propertyName;

    @OneToMany(mappedBy = "property", fetch = FetchType.LAZY )
    @Fetch(FetchMode.SUBSELECT)
    private List<BlockModel> block;

    @OneToMany(mappedBy = "property", fetch = FetchType.LAZY )
    @Fetch(FetchMode.SUBSELECT)
    private List<BookingModel> booking;
}
