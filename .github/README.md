<div align="justify">
  <div align="center">
    <picture>
      <img alt="Resume Logo" src="./docs/resume-name.png" />
    </picture>
  </div>

  <hr>

  <h3>1. ABSTRACT</h3>
<p>
  <em>
    This project aims to provide web services that enable users to easily access and manage their professional and personal information. At the core of this initiative is a robust <strong>RESTful API</strong>, designed for seamless integration with various applications to enhance user experience and ensure efficient data management. Prioritizing <strong>usability</strong>, <strong>security</strong>, and <strong>flexibility</strong>, this platform is especially appealing to professionals seeking to enhance their online presence. By consolidating essential information, users can effectively highlight their skills and experiences, thereby increasing their visibility in the job market. The Resume project offers a comprehensive suite of features for managing <strong>Achievements</strong>, <strong>Education</strong>, <strong>Experience</strong>, <strong>Hobbies</strong>, <strong>Profile</strong>, <strong>Skills</strong>, and <strong>User</strong> data, allowing users to intuitively maintain and access their information. Ideal for those looking to improve their personal branding, Resume ensures a secure and personalized experience that simplifies the effective presentation of skills.
  </em>
</p>
<p>
  Este projeto tem como objetivo fornecer serviços na web que permitem aos usuários acessar e gerenciar suas informações profissionais e pessoais de forma simples. No centro do projeto está uma <strong>API RESTful</strong> robusta, projetada para facilitar a integração com diversas aplicações, melhorando a experiência do usuário e garantindo uma gestão de dados eficiente. A plataforma prioriza <strong>usabilidade</strong>, <strong>segurança</strong> e <strong>flexibilidade</strong>, sendo especialmente atraente para profissionais que desejam aumentar sua presença online. Ao consolidar informações essenciais, os usuários podem destacar suas habilidades e experiências de forma eficaz, aumentando sua visibilidade no mercado de trabalho. O projeto Resume oferece um conjunto abrangente de recursos para gerenciar <strong>Conquistas</strong>, <strong>Educação</strong>, <strong>Experiência</strong>, <strong>Hobbies</strong>, <strong>Perfil</strong>, <strong>Habilidades</strong> e dados de <strong>Usuários</strong>, permitindo que os usuários mantenham e acessem suas informações de maneira intuitiva. Ideal para quem busca aprimorar sua marca pessoal, o Resume garante uma experiência segura e personalizada, que simplifica a apresentação eficaz das habilidades.
</p>

<h3>2. STATUS OF THIS PROJECT</h3>
<p>
  <em>
    This project is part of the studies undertaken in the Web and Mobile Development - Full Stack specialization, where various relevant technological resources were explored, as outlined in the course syllabus. Currently, the project is in a phase of continuous improvement, with core functionalities fully functional. The development team is focused on continuous improvements aimed at enhancing user experience through features that increase interactivity and responsiveness. Recent updates have introduced improvements in loading speed, responsiveness, and dynamic customization options. Future plans include adding more interactive elements, expanding customization, and continually optimizing performance for a seamless user experience.
  </em>
</p>
<p>
  Este projeto faz parte dos estudos realizados na especialização em Desenvolvimento Web e Mobile - Full Stack, onde foram abordados diversos recursos tecnológicos relevantes, conforme a ementa do curso. O projeto encontra-se atualmente em fase de aprimoramento contínuo, com as principais funcionalidades já operacionais. A equipe de desenvolvimento está focada em melhorias contínuas, voltadas para aprimorar a experiência do usuário por meio de recursos que aumentam a interatividade e a responsividade. As atualizações recentes trouxeram melhorias na velocidade de carregamento, responsividade e opções de personalização dinâmica. Estão planejados aprimoramentos futuros para introduzir mais elementos interativos, expandir a personalização e otimizar continuamente o desempenho para uma experiência de usuário fluida.
</p>




  <h3>3. CLIENTES</h3>
  <div>
    <div>
      <img align="left" width="100" height="100" src="./docs/resume-web.png" />
      <p>
        Acesse <a href="https://resume.vercel.app/">resume.vercel.app</a>. Nossa aplicação web é uma interface moderna e intuitiva que permite aos usuários interagir com a <strong>API RESTful Resume</strong>. Com design responsivo, a plataforma oferece uma experiência fluida para gerenciar informações profissionais e pessoais. Confira o repositório em <a href="https://github.com/danylo-macelai/resume-web">Resume Web</a> e veja também as pessoas <a href="https://github.com/danylo-macelai/resume-web?tab=readme-ov-file#cases">que já aderiram</a> a essa transformação profissional!
      </p>
    </div>
    <div>
      <img align="left" width="100" height="100" src="./docs/resume-mobile.png" />
      <p>
        O aplicativo mobile Resume proporciona uma experiência moderna e intuitiva, permitindo aos usuários interagir eficientemente com a <strong>API RESTful Resume</strong>. Disponível para download na <a href="#">Play Store</a>, o app tem design responsivo que facilita o gerenciamento de informações profissionais e pessoais. Confira o repositório em <a href="https://github.com/danylo-macelai/resume-mobile">Resume Mobile</a> e destaque suas habilidades de forma prática!
      </p>
    </div>
  </div>

  <h3>4. ARQUITETURA DO SISTEMA</h3>
  <p>
    A arquitetura da <strong>API RESTful</strong> foi desenvolvida com <strong>Java</strong> e <strong>Spring Boot</strong>, combinando padrões robustos e boas práticas para garantir uma estrutura escalável e eficiente. O padrão <strong>MVC</strong> organizou a aplicação, onde controladores gerenciaram requisições HTTP, serviços encapsularam a lógica de negócios e entidades representaram os dados. O uso do padrão <strong>Repositório</strong> com <strong>Spring Data JPA</strong> simplificou o acesso ao banco de dados, permitindo operações CRUD e a transferência segura de dados. A implementação de <strong>HATEOAS</strong> facilitou a navegação entre os recursos, enquanto uma documentação clara e o versionamento da API asseguraram que os desenvolvedores pudessem utilizar a interface de forma eficaz. Medidas de segurança, como autenticação e tratamento de erros, também foram aplicadas para proteger os dados e melhorar a experiência do usuário. Outras práticas, como paginação e o uso correto de métodos HTTP, contribuíram para uma interação intuitiva e consistente. Juntas, essas abordagens mantiveram a API organizada, manutenível e de fácil consumo, promovendo tanto a clareza quanto a segurança.
  </p>

  <div align="center">
    <picture>
      <img alt="Diagrama de Classes" src="./docs/der.png" width="100%" />
    </picture>
    <p><sup>Diagrama de Classes</sup></p>
  </div>

</div>
