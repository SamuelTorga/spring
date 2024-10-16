package br.com.samueltorga.spring.mappers;

import br.com.samueltorga.spring.controller.dto.user.NewUserRequest;
import br.com.samueltorga.spring.controller.dto.user.UserResponse;
import br.com.samueltorga.spring.repository.entity.User;
import br.com.samueltorga.spring.repository.entity.UserDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "userDetail", source = "newUserRequest")
    User toUserEntity(NewUserRequest newUserRequest);

    UserDetail toUserDetailEntity(NewUserRequest newUserRequest);

    @Mapping(target = "user", source = "user")
    void referenceUserDetailWithUser(User user, @MappingTarget UserDetail userDetail);

    @Mapping(target = "id", source = "user.id")
    UserResponse toUserResponse(User user, UserDetail userDetail);

}
