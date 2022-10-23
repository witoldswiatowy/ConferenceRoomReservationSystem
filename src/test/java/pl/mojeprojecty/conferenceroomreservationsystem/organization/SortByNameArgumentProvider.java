package pl.mojeprojecty.conferenceroomreservationsystem.organization;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.springframework.data.domain.Sort;
import pl.mojeprojecty.conferenceroomreservationsystem.organization.model.OrganizationEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

public class SortByNameArgumentProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of(
                        Collections.emptyList(),
                        Sort.by(Sort.Direction.ASC, "name"),
                        Collections.emptyList()
                ),
                Arguments.of(
                        Arrays.asList(
                                new OrganizationEntity("zzz"),
                                new OrganizationEntity("aaa"),
                                new OrganizationEntity("bbb")
                        ),
                        Sort.by(Sort.Direction.ASC, "name"),
                        Arrays.asList(
                                new OrganizationEntity("aaa"),
                                new OrganizationEntity("bbb"),
                                new OrganizationEntity("zzz")
                        )
                ),
                Arguments.of(
                        Arrays.asList(
                                new OrganizationEntity("zzz"),
                                new OrganizationEntity("aaa"),
                                new OrganizationEntity("bbb")
                        ),
                        Sort.by(Sort.Direction.DESC, "name"),
                        Arrays.asList(
                                new OrganizationEntity("zzz"),
                                new OrganizationEntity("bbb"),
                                new OrganizationEntity("aaa")
                        )
                )
        );
    }
}