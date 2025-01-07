package gabri.dev.minio.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer() {
            //Reescribir la interfaz de manejo de solicitudes CORS proporcionada por la clase padre
            @Override
            public void addCorsMappings(CorsRegistry registry){
                //Añadir ruta de mapeo
                registry
                        .addMapping("/**")
                        // Permitir qué dominios de origen
                        .allowedOrigins("*")
                        // Permitir si se envían cookies
                        .allowCredentials(true)
                        // Permitir qué métodos de solicitud (GET, POST, PUT, DELETE)
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        // Permitir qué cabeceras de solicitud
                        .allowedHeaders("*")
                        // Exponer qué cabeceras de respuesta (porque por defecto no se pueden obtener todas las cabeceras en el acceso cruzado)
                        .exposedHeaders("token");

            }

        };
    }
}
