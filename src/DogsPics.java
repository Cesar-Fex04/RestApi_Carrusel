import com.google.gson.Gson;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class DogsPics {
    public static final String BASE_URL = "https://dog.ceo/api/breed/";

    public static void main(String[] args) {
        // Paso 1: Solicitar al usuario el nombre de la raza
        String breed = JOptionPane.showInputDialog("Ingrese el nombre de la raza de perro (por ejemplo, 'labrador'):");

        if (breed != null && !breed.isEmpty()) {
            int n = 5; // Número de imágenes a mostrar
            DogsApiResponse response = query(breed, n);
            ArrayList<String> urls = response.getMessage();

            if (urls.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No se encontraron imágenes para la raza especificada.");
            } else {
                for (String u : urls) {
                    System.out.println(u);
                    EventQueue.invokeLater(() -> {
                        JFrame ex = new ImageView(u);
                        ex.setVisible(true);
                    });
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese un nombre de raza de perro válido.");
        }
    }

    public static DogsApiResponse query(String breed, int n) {
        DogsApiResponse response = null;
        try {
            URL u = new URL(BASE_URL + breed + "/images/random/" + n);

            BufferedReader in = new BufferedReader(new InputStreamReader(u.openStream()));
            String buffer;
            StringBuilder jsonText = new StringBuilder();
            while ((buffer = in.readLine()) != null) {
                jsonText.append(buffer);
            }
            in.close();
            Gson gson = new Gson();
            response = gson.fromJson(jsonText.toString(), DogsApiResponse.class);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response;
    }
}
