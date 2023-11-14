package com.ingweb.EmplyeService;


import org.springframework;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
class Employe {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String nom;
	private String prenom;
	private String adressMail;

}

@Repository
interface EmployeRepository extends JpaRepository<Employe,Long> {
}

@RestController
class emplRestControler {

	private EmployeRepository employeRepository;
	public emplRestControler(EmployeRepository employeRepository) {
		this.employeRepository =employeRepository;
	}
	//http://localhost:8081/employes
	@GetMapping(path="/employes")
	public List<Employe> getAllEmp(){
		return employeRepository.findAll();
	}
	@GetMapping(value="/employes/{id}")
	public Employe getEmp(@PathVariable(name="id") Long id){
		return employeRepository.findById(id).get();
	}

	@PutMapping(value="/employes/{id}")
	public Employe updateEmp(@PathVariable(name="id") Long id, @RequestBody Employe emp){
		emp.setId(id);
		return employeRepository.save(emp);
	}
	@PostMapping(value="/employes")
	public Employe save(@RequestBody Employe emp){
		return employeRepository.save(emp);

	}
	@DeleteMapping(value="/employes/{id}")
	public void delete(@PathVariable(name="id") Long id){
		employeRepository.deleteById(id);
	}
}


@SpringBootApplication
public class EmplyeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmplyeServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner star(EmployeRepository employeRepository) {
		return args ->{
			employeRepository.save(new Employe(null,"Aichaoui","Aicha","aicha1.aicha1@adressemail.com"));
			employeRepository.save(new Employe(null,"Mohamed","Ahmed","mohamed.ahmed@adressemail.com"));
			employeRepository.save(new Employe(null,"Gues","Bilal"," gues.bilal@adressemail.com"));
			employeRepository.findAll().forEach(emp-> {System.out.println(emp.toString());});
		};
	}
}
