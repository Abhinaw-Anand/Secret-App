package com.example.SpringInitial.Repository;

import com.example.SpringInitial.Model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long>
{
}
