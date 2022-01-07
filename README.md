# SpringBoot_Web_Project
Spring Boot의 MVC패턴을 기반으로 만든 중고거래 계시판입니다. 당근마켓을 사용하면서 지도 중심으로 게시물을 확인하면 어떨까 하는 방식으로 만든 프로젝트 입니다.

# 사용기술
- Spring Boot 2.5.2
- Spring Security
- Spring Batch
- JPA
- Querydsl
- Oracle
- JQuery
- ToastGrid
- SummerNote WYSIWYG Editor
- Thymeleaf
- Kakao Map API
- RestTemplate (해당 프로젝트를 [프로젝트 분리 API서버](https://github.com/minsu9605/SpringBoot_webProject_Backend) 와 [프로젝트 분리 Front](https://github.com/minsu9605/SpringBoot_webProject_Frontend) 중에서 [**프로젝트 분리 API서버**](https://github.com/minsu9605/SpringBoot_webProject_Backend)에  사용)

# 기능설명
- ## 회원가입 및 로그인
	- 회원가입 진행시 아이디, 패스워드, 닉네임, 생년월일, 성별을 입력합니다.
	- 아이디는 이메일 형식이며 중복체크를 통해 중복 이메일 가입을 방지하고 있습니다.
	- 패스워드는 패스워드 확인을 이용해 체크하며 `BCryptPasswordEncoder`를 이용해 암호화 하여 DB에 저장하였습니다.
	- 닉네임은 웹페이지 이용시 주로 사용되므로 닉네임 역시 중복체크를 통해 중복 닉네임 가입을 방지하고 있습니다.

- ## 회원
	- 회원은 `Member`라는 Role을 소유하고 있습니다. 
	- 회원은 공지사항을 제외한 게시물을 작성하고 댓글 및 대댓글도 작성이 가능합니다. 
	- 회원은 MyPage에서 본인이 작성한 게시물들을 관리 할 수 있습니다.
	- 회원은 개인정보관리 페이지에서 패스워드 및 회원정보를 수정 할 수 있습니다. 이때 패스워드 변경 시 기존 패스워드를 한번 더 체크합니다. 회원정보 수정 시 닉네임은 중복체크를 통해 변경이 가능합니다.
- ##  관리자
	- Spring Security를 이용하여 로그인 과정 시 로그인을 완료하면 회원의 Role이 `Admin`과`Member` 로 구분이 되어있습니다.
	- 관리자는 모든 게시물 및 댓글들의 삭제 권한이 주어지고 공지사항에 글을 작성할 수 있는 권한이 주어집니다.
	- 관리자는 모든 회원들의 정보를 확인할 수 있고 일부 회원의 정보를 편집 수정 할 수 있습니다.
	- 관리자는 관리페이지에서 주기적으로 실행되는 `Spring Batch`를 이용하여 차트 형식으로 게시물의 판매 동향을 확인 할 수 있습니다.

![hello](https://user-images.githubusercontent.com/75827783/148492270-efae1dd0-55bd-4e34-ae2c-ec9f81653387.png)
![image](https://user-images.githubusercontent.com/75827783/148492530-d041c152-26dc-4dc7-ba8b-347522d0098d.png)

- ## 게시물 작성 
	- 모든 회원은 게시물을 작성 할 수 있습니다. 게시물 작성 시 `카테고리, 금액, 장소, 제목, 내용`을 필수 적으로 작성 하셔야 합니다. 
	- 장소 지정하기 클릭 시 `Kakao Map API`를 이용하여 팝업창이 생성 됩니다. 검색이나 지도 이동을 통하여 마커 클릭 시 해당 장소가 거래 장소로 등록이 됩니다.
	- 게시물 작성은 `SummerNote Editor`를 사용하였습니다. 이미지 첨부도 구현하였습니다.
	- 해당 게시물이 작성되면 자동적으로 `Map`메뉴에도 게시물이 표시 됩니다.
	![image](https://user-images.githubusercontent.com/75827783/148492756-d8206b6a-3a34-4011-8f6a-dabf3c2652de.png)

- ## 게시물 리스트 & 상세 보기
	- 게시물 리스트는 `Toast Grid`를 이용하여 구현하였습니다. 게시물의 상태(판매중, 판매완료)를 확인 할 수있고, 업데이트 날짜 등을 확인 할 수 있습니다.
	- 검색과 페이징도 구현하였습니다. 검색은 제목, 작성자, 내용으로 검색이 가능합니다.
	- 게시물 클릭 시 해당 이미지는 `Slider`라이브러리를 이용하여 구현하였고, 해당 금액, 거래장소, 댓글 등을 확인 할 수있습니다.
	- 해당 장소 확인 클릭 시 `카카오지도`로 큰 창으로 열거나 바로 길찾기를 수행 할 수 있습니다.
	- 댓글 및 대댓글은 모든 사용자가 커밋 할 수 있으나 삭제 진행 시 대댓글이 존재 할 경우 모댓글의 삭제는 불가능 합니다.
	- 이때 관리자는 댓글 삭제 시 대댓글 까지 한번에 삭제 가능합니다. 
	- 해당 게시물이 판매완료가 되었을 때 글 작성자는 판매완료 버튼을 눌러 게시글을 수정 할 수 있습니다.
![image](https://user-images.githubusercontent.com/75827783/148492834-ba451565-fa62-4742-ad0f-f58859b58b70.png)
![image](https://user-images.githubusercontent.com/75827783/148492880-e48e9d43-a256-40e0-b8ae-bc484663787b.png)
![image](https://user-images.githubusercontent.com/75827783/148493559-6e376d42-6b43-462e-b7eb-c4b74480da88.png)
![image](https://user-images.githubusercontent.com/75827783/148493613-40882a80-08b1-47c0-b951-90c27144826c.png)
![image](https://user-images.githubusercontent.com/75827783/148493737-6247f6e0-e0bf-43c0-a24e-62a073104813.png)

- ## 지도에 게시물 표시
	- `Kakao Map API`를 이용하여 화면에 나타나는 좌표값을 받아 해당 위치에 존재하는 게시물을 표시하였습니다.
	- 검색이나 드래그, 내 위치로 이동을 통해 게시물을 확인 가능하며 카테고리별 마커를 다르게 설정하였습니다.
	- 해당 마커 클릭 시 해당 게시물로 이동합니다. 
	- 클러스터를 이용하여 지도를 축소하였을 때 보기 쉽게 하였습니다.
![image](https://user-images.githubusercontent.com/75827783/148493882-86a94709-930c-4beb-8a67-c752cb67fe8e.png)
![image](https://user-images.githubusercontent.com/75827783/148493898-e4429058-bd33-47b2-b507-ca037a20aa54.png)
- ## 오래된 게시물 알림
	- 상단바에 위치한 종 모양의 아이콘을 클릭하면 게시물을 올린지 1주일이 넘었지만 아직 판매되지 않은 게시무을 확인하여 갯수등을 표시해 줍니다.
	- 해당 로직은 `Spring Batch`와 `Scheduler`를 이용하여 매일 특정시간에 수행하도록 설정하였습니다.
	- 해당 알람을 통해 게시물로 이동하기 버튼을 클릭 시 해당 알람은 다시 뜨지 않습니다. 
	- 알람이 사라진 게시물이나 `오래된게시물`을 확인하고 싶으면 마이페이지에 오래된 게시물 버튼을 클릭하면 확인이 가능합니다.
- ## 공지사항
	- 공지사항게시판은 관리자만 작성이 가능하며 일반 회원은 해당 게시물의 읽기 권한만 주어집니다.
 # 프로젝트 분리
 해당 프로젝트는 프런트와 백을 한 프로젝트에 구현하였습니다. RestAPI를 학습하기 위해 해당 프로젝트를 다음과 같이 분리하였습니다.
 [프로젝트 분리 API서버](https://github.com/minsu9605/SpringBoot_webProject_Backend),  
  [프로젝트 분리 Front](https://github.com/minsu9605/SpringBoot_webProject_Frontend)
  # 개인 블로그
  해당 프로젝트는 아래 블로그에 정리 하였습니다. 
  [개인 기술 블로그](https://velog.io/@alstn_dev?tag=%EC%9D%B8%ED%84%B4%EC%8B%AD) 
