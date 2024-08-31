package com.example.accounts.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;
/*\
purpose of this class is to tell spring data JPA to handle created by , updated by , lastmodified by etc.
Sp that we don't need to handle it manually.
 */

@Component("auditAwareImpl")
public class AuditAwareImpl implements AuditorAware {

    @Override
    public Optional getCurrentAuditor() {
        return Optional.of("Account_MS"); // to get gurrent Auditor
    }
// we are returning ACOUNTMS as default value for created by, updated by etc.



}
