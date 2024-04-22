package com.cycastic.library_management.controllers;

import com.cycastic.library_management.models.CountReturn;
import com.cycastic.library_management.models.member.MemberCreationRequest;
import com.cycastic.library_management.models.member.MemberDetails;
import com.cycastic.library_management.models.member.MemberDetailsList;
import com.cycastic.library_management.models.responses.AffectedResponse;
import com.cycastic.library_management.models.responses.StringIdResponse;
import com.cycastic.library_management.services.MembersService;
import lombok.RequiredArgsConstructor;
import org.jooq.exception.MappingException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MembersController {
    private final MembersService membersService;

    @PutMapping
    public StringIdResponse createMember(@RequestBody MemberCreationRequest request){
        try {
            var ret = membersService.createMember(request);
            return new StringIdResponse(ret);
        } catch (DuplicateKeyException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Mã thành viên bị trùng");
        } catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Một hoặc nhiều trường bị thiếu");
        }
    }

    @GetMapping(produces = { "application/json" })
    public MemberDetails getMember(@RequestParam("id") String id){
        try {
            var ret = membersService.getMember(id);
            if (ret == null)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy");
            return ret;
        } catch (MappingException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cast failed");
        }
    }
    @GetMapping(value = {"search"}, produces = { "application/json" })
    public MemberDetailsList searchMembers(@RequestParam("pattern") String pattern){
        try {
            return new MemberDetailsList(membersService.searchMember(pattern));
        } catch (MappingException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cast failed");
        }
    }

    @GetMapping(value = { "count/all" }, produces = { "application/json" })
    public CountReturn countMember(){
        return new CountReturn(membersService.countMember());
    }

    @PatchMapping
    public void editMember(@RequestBody MemberCreationRequest request){
        membersService.updateMember(request);
    }

    @DeleteMapping
    public AffectedResponse deleteMember(@RequestParam("id") String id){
        try {
            var ret = membersService.deleteMember(id);
            return new AffectedResponse(ret);
        } catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Một hoặc nhiều trường đang được tham chiếu từ bảng khác");
        }
    }
}
