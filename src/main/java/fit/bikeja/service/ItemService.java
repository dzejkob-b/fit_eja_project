package fit.bikeja.service;

import fit.bikeja.dao.ItemDao;
import fit.bikeja.dao.UserDao;
import fit.bikeja.dto.ItemDto;
import fit.bikeja.entity.Item;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class ItemService {

    @PersistenceContext
    private EntityManager em;

    @Inject
    ItemDao itemDao;

    @Inject
    UserDao userDao;

    @PostConstruct
    public void onInit() {
    }

    public Optional<ItemDto> getItem(int id) {
        Optional<ItemDto> item = Optional.empty();

        try {
            item = Optional.of(new ItemDto(this.itemDao.getOne(id)));
        } catch (EntityNotFoundException ignored) {
        }

        return item;
    }

    public Item createItem(ItemDto item) {
        Item it = item.toEntity();
        it.setCreatedBy(this.userDao.getOne(item.getCreatedBy_id()));
        this.itemDao.save(it);
        return it;
    }

    public void updateItem(int id, ItemDto item) {
        Item it = this.itemDao.getOne(id);

        it.setCaption(item.getCaption());
        it.setValue(item.getValue());
        it.setCreatedBy(this.userDao.getOne(item.getCreatedBy_id()));

        this.itemDao.save(it);
    }

    public void deleteItem(int id) {
        this.itemDao.deleteById(id);
    }

    public Collection<ItemDto> getAllItems() {
        return this.itemDao.findAll().stream().map(ItemDto::new).collect(Collectors.toList());
    }
}
