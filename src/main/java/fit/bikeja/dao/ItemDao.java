package fit.bikeja.dao;

import fit.bikeja.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.enterprise.context.ApplicationScoped;
import java.util.Collection;

@ApplicationScoped
public interface ItemDao extends JpaRepository<Item, Integer> {

    @Query("SELECT i FROM Item i WHERE i.createdBy.id = :createdBy_id")
    Collection<Item> getItemsByUser(@Param("createdBy_id") int createdBy_id);

}
