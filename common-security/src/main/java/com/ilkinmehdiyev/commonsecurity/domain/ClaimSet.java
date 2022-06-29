package com.ilkinmehdiyev.commonsecurity.domain;

import java.util.Set;

public record ClaimSet(String key, Set<String> claims) {
}
