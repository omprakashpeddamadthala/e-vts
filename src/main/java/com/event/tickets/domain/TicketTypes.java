package com.event.tickets.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.sql.results.graph.Fetch;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "ticket_type")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketTypes {

    @Id
    @Column(name = "id",updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "price",nullable = false)
    private BigDecimal price ;

    @Column(name = "total_available",nullable = false)
    private Integer totalAvailable ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "events_id")
    private Event event;

    //TODO tickets

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TicketTypes that = (TicketTypes) o;
        return Objects.equals( id, that.id ) && Objects.equals( name, that.name ) && Objects.equals( price, that.price ) && Objects.equals( totalAvailable, that.totalAvailable ) && Objects.equals( createdAt, that.createdAt ) && Objects.equals( updatedAt, that.updatedAt );
    }

    @Override
    public int hashCode() {
        return Objects.hash( id, name, price, totalAvailable, createdAt, updatedAt );
    }
}
