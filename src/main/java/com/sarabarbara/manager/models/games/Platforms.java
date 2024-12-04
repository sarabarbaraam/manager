package com.sarabarbara.manager.models.games;

import lombok.*;

import java.util.Objects;

/**
 * Platforms class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 20/11/2024
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Platforms {

    /**
     * The windows
     */

    private boolean windows;

    /**
     * The mac
     */

    private boolean mac;

    /**
     * The linux
     */

    private boolean linux;

    /**
     * The equals
     */

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Platforms platforms)) return false;
        return isWindows() == platforms.isWindows()
                && isMac() == platforms.isMac()
                && isLinux() == platforms.isLinux();
    }

    /**
     * The hashCode
     */

    @Override
    public int hashCode() {
        return Objects.hash(isWindows(), isMac(), isLinux());
    }

}
