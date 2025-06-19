package com.tracker.backend;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tracker.backend.config.AppConstants;
import com.tracker.backend.entities.Role;
import com.tracker.backend.repositories.RoleRepository;

@SpringBootApplication
public class BudgetTrackerApisApplication implements CommandLineRunner {
	@Autowired
	private RoleRepository roleRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(BudgetTrackerApisApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Override
	public void run(String... args) throws Exception {
//		System.out.println(this.encoder.encode("TomCruise"));
		try {
			Role role = new Role();
			role.setId(AppConstants.ADMIN_USER);
			role.setName("ROLE_ADMIN");
			
			Role role2= new Role();
			role2.setName("ROLE_NORMAL");
			role2.setId(AppConstants.NORMAL_USER);
			
			List<Role> roles= List.of(role, role2);
			List<Role> result= this.roleRepo.saveAll(roles);
			result.forEach( r -> {
				System.out.print("Role added: "+ r.getName());
				});
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}