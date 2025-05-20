package com.adraga_mesas.reservasrestaurante.config;

import com.adraga_mesas.reservasrestaurante.model.Mesa;
import com.adraga_mesas.reservasrestaurante.model.Usuario;
import com.adraga_mesas.reservasrestaurante.repository.MesaRepository;
import com.adraga_mesas.reservasrestaurante.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MesaRepository mesaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Initialize test user if it doesn't exist
        if (usuarioRepository.findByEmail("test@example.com").isEmpty()) {
            Usuario usuario = new Usuario();
            usuario.setNome("Test User");
            usuario.setEmail("test@example.com");
            usuario.setTelefone("123456789");
            usuario.setSenha(passwordEncoder.encode("password"));
            usuario.setRole("ROLE_USER");
            usuarioRepository.save(usuario);
        }

        // Initialize admin user if it doesn't exist
        if (usuarioRepository.findByEmail("admin@example.com").isEmpty()) {
            Usuario admin = new Usuario();
            admin.setNome("Admin User");
            admin.setEmail("admin@example.com");
            admin.setTelefone("987654321");
            admin.setSenha(passwordEncoder.encode("adminpassword"));
            admin.setRole("ROLE_ADMIN");
            usuarioRepository.save(admin);
        }

        // Initialize tables if they don't exist
        if (mesaRepository.count() == 0) {
            Mesa mesa = new Mesa();
            mesa.setNumero(1);
            mesa.setLugares(4);
            mesaRepository.save(mesa);

            Mesa mesa2 = new Mesa();
            mesa2.setNumero(2);
            mesa2.setLugares(6);
            mesaRepository.save(mesa2);
        }
    }
}
