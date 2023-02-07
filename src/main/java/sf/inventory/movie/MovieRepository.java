package sf.inventory.movie;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MovieRepository extends CrudRepository<Movie, Long> {
    public List<Movie> findByBarcode(String barcode);

}
