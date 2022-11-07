package admin;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class EntityManager {
    private final List<UsedForFront> itemEntities = List.of(
            new Gpu(),
            new Cpu(),
            new Ram()
    );

    private final Map<String, ItemDao> itemDao = Map.of(
            Configs.GPU_TITLE, new ItemDao(Gpu.class),
            Configs.CPU_TITLE, new ItemDao(Cpu.class),
            Configs.RAM_TITLE, new ItemDao(Ram.class)
    );

    public EntityManager() throws SQLException {
    }

    public List<UsedForFront> getItemEntities() {
        return List.copyOf(itemEntities);
    }

    public int save(UsedForFront item) throws SQLException,
            NoSuchFieldException,
            IllegalAccessException {
        return itemDao.get(item.getTitle()).save(item);
    }

    public int delete(UsedForFront item, int id) throws SQLException {
        return itemDao.get(item.getTitle()).deleteById(id);
    }

    public List<UsedForFront> getItemsByConstraint(UsedForFront constraint, int offset, String sort) throws SQLException,
            NoSuchFieldException,
            IllegalAccessException,
            InstantiationException {
        return itemDao.get(constraint.getTitle()).getByConstraint(constraint, offset, sort);
    }

    public List<Map<String, Integer>> getFilters(UsedForFront constraint) throws SQLException,
            NoSuchFieldException,
            IllegalAccessException {
        return itemDao.get(constraint.getTitle()).getFilters(constraint);
    }

    public UsedForFront getItemById(UsedForFront item, int id) throws SQLException,
            IllegalAccessException,
            InstantiationException {
        return itemDao.get(item.getTitle()).getItemById(id);
    }

    public int updateItemById(UsedForFront item, int id) throws SQLException,
            NoSuchFieldException,
            IllegalAccessException {
        return itemDao.get(item.getTitle()).updateItemById(item, id);
    }

    public int getPageCount(UsedForFront constraint) throws SQLException, NoSuchFieldException, IllegalAccessException {
        return itemDao.get(constraint.getTitle()).getPageCount(constraint);
    }
}
