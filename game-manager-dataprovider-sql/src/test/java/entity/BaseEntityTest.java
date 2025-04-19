package entity;

import com.tracktainment.gamemanager.entity.BaseEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BaseEntityTest {

    @Test
    void shouldCreateBaseEntityUsingBuilder() {
        // Arrange
        Long dbId = 1L;
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now().plusDays(1);

        // Act
        BaseEntity baseEntity = BaseEntity.builder()
                .dbId(dbId)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();

        // Assert
        assertEquals(dbId, baseEntity.getDbId());
        assertEquals(createdAt, baseEntity.getCreatedAt());
        assertEquals(updatedAt, baseEntity.getUpdatedAt());
    }

    @Test
    void shouldCreateEmptyBaseEntityUsingNoArgsConstructor() {
        // Act
        BaseEntity baseEntity = new BaseEntity();

        // Assert
        assertNull(baseEntity.getDbId());
        assertNull(baseEntity.getCreatedAt());
        assertNull(baseEntity.getUpdatedAt());
    }

    @Test
    void shouldUseSettersAndGetters() {
        // Arrange
        BaseEntity baseEntity = new BaseEntity();
        Long dbId = 2L;
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now().plusDays(1);

        // Act
        baseEntity.setDbId(dbId);
        baseEntity.setCreatedAt(createdAt);
        baseEntity.setUpdatedAt(updatedAt);

        // Assert
        assertEquals(dbId, baseEntity.getDbId());
        assertEquals(createdAt, baseEntity.getCreatedAt());
        assertEquals(updatedAt, baseEntity.getUpdatedAt());
    }

    @Test
    void shouldTestIsNewMethod() {
        // Arrange
        BaseEntity newEntity = new BaseEntity();

        BaseEntity existingEntity = new BaseEntity();
        existingEntity.setUpdatedAt(LocalDateTime.now());

        // Act & Assert - Using reflection to access private method
        try {
            java.lang.reflect.Method isNewMethod = BaseEntity.class.getDeclaredMethod("isNew");
            isNewMethod.setAccessible(true);

            assertTrue((Boolean) isNewMethod.invoke(newEntity), "Entity with null updatedAt should be considered new");
            assertFalse((Boolean) isNewMethod.invoke(existingEntity), "Entity with non-null updatedAt should not be considered new");
        } catch (Exception e) {
            fail("Failed to test isNew method: " + e.getMessage());
        }
    }

    @Test
    void shouldCreateBaseEntityWithAllArgsConstructor() {
        // Arrange
        Long dbId = 3L;
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now().plusDays(1);

        // Act
        BaseEntity baseEntity = new BaseEntity(dbId, createdAt, updatedAt);

        // Assert
        assertEquals(dbId, baseEntity.getDbId());
        assertEquals(createdAt, baseEntity.getCreatedAt());
        assertEquals(updatedAt, baseEntity.getUpdatedAt());
    }

    @Test
    void shouldImplementEqualsAndHashCode() {
        // Arrange
        Long dbId1 = 1L;
        Long dbId2 = 2L;

        BaseEntity entity1 = BaseEntity.builder().dbId(dbId1).build();
        BaseEntity entity2 = BaseEntity.builder().dbId(dbId1).build(); // Same dbId
        BaseEntity entity3 = BaseEntity.builder().dbId(dbId2).build(); // Different dbId

        // Assert
        assertEquals(entity1, entity1); // Same object reference
        assertEquals(entity1, entity2); // Equal by dbId
        assertNotEquals(entity1, entity3); // Different dbId
        assertEquals(entity1.hashCode(), entity2.hashCode()); // Same hash code for equal objects
        assertNotEquals(entity1.hashCode(), entity3.hashCode()); // Different hash code for different objects
        assertNotEquals(entity1, null); // Not equal to null
        assertNotEquals(entity1, "not a BaseEntity"); // Not equal to different type
    }

    @Test
    void shouldImplementToString() {
        // Arrange
        Long dbId = 4L;
        BaseEntity baseEntity = BaseEntity.builder()
                .dbId(dbId)
                .build();

        // Act
        String toString = baseEntity.toString();

        // Assert
        assertNotNull(toString);
        assertFalse(toString.isEmpty());
        assertTrue(toString.contains("BaseEntity"));
        assertTrue(toString.contains(dbId.toString()));
    }
}
