package springboot.local.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
public class Application {

    private Map<Integer, String> data = new HashMap<>();

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "this is Spring-boot!") String name) {
      return String.format("Hello %s!", name);
    }

    @PostMapping("/")
    public String create(@RequestParam(value = "id") int id, @RequestParam(value = "name") String name) {
        data.put(id, name);
        return "Data created successfully";
    }

    @GetMapping("/")
    public String readall() {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<Integer, String> entry : data.entrySet()) {
            result.append("id: ").append(entry.getKey()).append(", name: ").append(entry.getValue()).append("\n");
        }
        return result.toString();
    }
    

    @GetMapping("/{id}")
    public String read(@PathVariable("id") int id) {
        String name = data.get(id);
        return name != null ? name : "Data not found";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable("id") int id, @RequestParam(value = "name") String name) {
        if (data.containsKey(id)) {
            data.put(id, name);
            return "Data updated successfully";
        }
        return "Data not found";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        if (data.containsKey(id)) {
            data.remove(id);
            return "Data deleted successfully";
        }
        return "Data not found";
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
