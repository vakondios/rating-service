package gr.xe.rating.service.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("dev")
class DbUtilsTest {

    /**
     * Test with missed string field to destination
     */
    @Test
    void genericMapperTest1() {
        A a = new A();
        a.setFirstname("Antonis");
        a.setLastname("Vakondios");
        a.setAddress("Athens");

        B b= new B();
        DbUtils.genericMapper(a, b);
        assertEquals(a.getFirstname(), b.getFirstname());
        assertEquals(a.getLastname(), b.getLastname());
    }

    /**
     * Test with missed object field to destination
     */
    @Test
    void genericMapperTest2() {
        Address address = new Address("street", "city", "zip");
        AA a = new AA();
        a.setFirstname("Antonis");
        a.setLastname("Vakondios");
        a.setAddress(address);

        BB b= new BB();
        DbUtils.genericMapper(a, b);
        assertEquals(a.getFirstname(), b.getFirstname());
        assertEquals(a.getLastname(), b.getLastname());
    }

    /**
     * Test with object (all fields exits)
     */
    @Test
    void genericMapperTest3() {
        Address address = new Address("street", "city", "zip");
        AA a = new AA();
        a.setFirstname("Antonis");
        a.setLastname("Vakondios");
        a.setAddress(address);

        BBAddr b= new BBAddr();
        DbUtils.genericMapper(a, b);
        assertEquals(a.getFirstname(), b.getFirstname());
        assertEquals(a.getLastname(), b.getLastname());
        assertEquals(a.getAddress().city, b.getAddress().city);
    }

    /**
     * Test with List object (all fields exits)
     */
    @Test
    void genericMapperTest4() {
        Address address = new Address("street", "city", "zip");
        List<Address> addresses = new ArrayList<>();
        addresses.add(address);
        addresses.add(address);

        AAA a = new AAA();
        a.setFirstname("Antonis");
        a.setLastname("Vakondios");
        a.setAddresses(addresses);

        BBB b= new BBB();
        DbUtils.genericMapper(a, b);
        assertEquals(a.getFirstname(), b.getFirstname());
        assertEquals(a.getLastname(), b.getLastname());
        assertEquals((a.getAddresses().get(0).getCity()), b.getAddresses().get(0).getCity());
    }

    /**
     * Test with array [] of object (all fields exits)
     */
    @Test
    void genericMapperTest5() {
        Address address = new Address("street", "city", "zip");
        List<Address> addresses = new ArrayList<>();
        addresses.add(address);
        addresses.add(address);

        Address[] arrayAddress = new Address[addresses.size()];
        addresses.toArray(arrayAddress);

        AAAA a = new AAAA();
        a.setFirstname("Antonis");
        a.setLastname("Vakondios");
        a.setAddresses(arrayAddress);

        BBBB b= new BBBB();
        DbUtils.genericMapper(a, b);
        assertEquals(a.getFirstname(), b.getFirstname());
        assertEquals(a.getLastname(), b.getLastname());
        assertEquals(a.getAddresses()[0].getCity(), b.getAddresses()[0].getCity());
        assertEquals(a.getAddresses().length, b.getAddresses().length);
    }

    @Data
    static
    class A {
        private String firstname;
        private String lastname;
        private String address;
    }

    @Data
    static
    class B {
        private String firstname;
        private String lastname;
    }

    @Data
    static
    class AA {
        private String firstname;
        private String lastname;
        private Address address;
    }

    @Data
    static
    class BB {
        private String firstname;
        private String lastname;
    }

    @Data
    static
    class BBAddr {
        private String firstname;
        private String lastname;
        private Address address;
    }

    @Data
    static
    class AAA {
        private String firstname;
        private String lastname;
        private List<Address> addresses;
    }

    @Data
    static
    class BBB {
        private String firstname;
        private String lastname;
        private List<Address> addresses;
    }

    @Data
    static
    class AAAA {
        private String firstname;
        private String lastname;
        private Address[] addresses;
    }

    @Data
    static
    class BBBB {
        private String firstname;
        private String lastname;
        private Address[] addresses;
    }

    @Data
    @AllArgsConstructor
    static
    class Address {
        private String street;
        private String city;
        private String zip;
    }
}