package guru.springframework.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by yaguar.
 */
@NoArgsConstructor
@Setter
@Getter
public class NotesCommand {
    private Long id;
    private String description;
}
