package tests;


import com.training.java.model.entity.Dish;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.math.BigDecimal;

public class TestDish {
    private Dish dish;

    @Before
    public void constr() {
        dish = new Dish();
    }

    @Test(expected = NumberFormatException.class)
    public void testSetPrice() {
        dish.setPrice(BigDecimal.valueOf(Double.valueOf("123,23")));
    }


    @Test(timeout = 1000)
    public void testSetName() {
        dish.setName(null);
        Assert.assertNull(dish.getName());
    }

    @Test
    public void testCompare() {
        dish.setName("Dish1");
        dish.setFileName("file1");
        Dish dish1 = new Dish();
        dish1.setName("Dish1");
        dish1.setFileName("file2");
        Assert.assertEquals(dish1.compareTo(dish), 0);
    }

    @Test
    public void testEquals() {
        dish.setName("Dish1");
        dish.setFileName("file1");
        Dish dish1 = new Dish();
        dish1.setName("Dish1");
        dish1.setFileName("file2");
        Assert.assertNotSame(dish1, dish);
    }

    @After
    public void postmethod() {
        dish = null;
    }
}