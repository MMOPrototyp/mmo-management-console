package com.jukusoft.mmo.data.entity.map;

import com.jukusoft.mmo.data.entity.AbstractEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * a zone is a collection of one or more regions.
 * For the first attempts, the game has only one zone.
 *
 * Limitations:
 * NPCs can only travels within a zone (between regions & maps), not between zones!
 */
@Entity
@Table(name = "game_zones", indexes = {
        //@Index(columnList = "email", name = "email_idx"),
}, uniqueConstraints = {
        //@UniqueConstraint(columnNames = "username", name = "username_uqn")
})
@Cacheable//use second level cache
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ZoneEntity extends AbstractEntity {

    //

}
