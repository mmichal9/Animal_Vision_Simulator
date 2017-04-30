#extension GL_OES_standard_derivatives : enable
precision mediump float;

uniform vec3                iResolution;
uniform vec3                iChannelResolution;
uniform float               iGlobalTime;
uniform sampler2D           iChannel0;
varying vec2                texCoord;


void mainImage( out vec4 fragColor, in vec2 fragCoord )
{

    vec2 uv = fragCoord.xy;
    vec4 color = texture( iChannel0, fragCoord/iResolution.xy );
    vec3 colormatrix = vec3(color.rgb);
    vec3 bgr = vec3(color.bgr);

    fragColor = vec4(bgr,1.0);
}

void main() {
    mainImage(gl_FragColor, texCoord.xy*iResolution.xy);
}
